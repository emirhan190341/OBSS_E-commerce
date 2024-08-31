package com.obss_final_project.e_commerce.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.request.product.CreateProductRequest;
import com.obss_final_project.e_commerce.dto.request.product.UpdateProductRequest;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;
import com.obss_final_project.e_commerce.elasticsearch.ESUtil;
import com.obss_final_project.e_commerce.elasticsearch.ProductES;
import com.obss_final_project.e_commerce.elasticsearch.ProductESRepository;
import com.obss_final_project.e_commerce.dto.request.product.SearchDtoRequest;
import com.obss_final_project.e_commerce.exception.product.ProductNotFoundException;
import com.obss_final_project.e_commerce.exception.seller.SellerNotFoundException;
import com.obss_final_project.e_commerce.mapper.ProductMapper;
import com.obss_final_project.e_commerce.model.Product;
import com.obss_final_project.e_commerce.model.Seller;
import com.obss_final_project.e_commerce.repository.ProductRepository;
import com.obss_final_project.e_commerce.repository.SellerRepository;
import com.obss_final_project.e_commerce.service.FileService;
import com.obss_final_project.e_commerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final FileService fileService;

    private final ElasticsearchClient client;
    private final ProductESRepository productESRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${product-topic}")
    private String productTopic;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              SellerRepository sellerRepository,
                              FileService fileService,
                              ElasticsearchClient client,
                              ProductESRepository productESRepository,
                              KafkaTemplate<String, Object> kafkaTemplate,
                              ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.fileService = fileService;
        this.client = client;
        this.productESRepository = productESRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;

    }

    @Override
    @Transactional
    public ProductResponse saveProduct(Long sellerId, CreateProductRequest request, MultipartFile file) throws JsonProcessingException {


        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + sellerId));


        Product product = ProductMapper.mapToProduct(request);
        product.setSeller(seller);


        if (file != null && !file.isEmpty()) {
            ResponseEntity<String> stringResponseEntity = fileService.uploadFile(file);
            product.setLogo(stringResponseEntity.getBody());
        }


        Product savedProduct = productRepository.save(product);

        String s = objectMapper.writeValueAsString(savedProduct);

        kafkaTemplate.send(productTopic, s);

        log.info("ProductServiceImpl.saveProduct product: {}", product);
        return ProductMapper.mapToProductResponse(savedProduct);
    }

    @Override
    public List<ProductES> getAllDataFromIndex(String indexName) {

        try {
            var supplier = ESUtil.createMatchAllQuery();
            log.info("Elasticsearch query {}", supplier.toString());

            SearchResponse<ProductES> response = client.search(
                    q -> q.index(indexName).query(supplier), ProductES.class);

            log.info("Elasticsearch response {}", response.toString());

            return extractItemsFromResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProductES> searchProductByFieldAndValue(SearchDtoRequest dto) {
        SearchResponse<ProductES> response = null;
        try {
            Supplier<Query> querySupplier = ESUtil.buildQueryForFieldAndValue(dto.getFieldName().get(0),
                    dto.getSearchValue().get(0));

            log.info("Elasticsearch query {}", querySupplier);

            response = client.search(q -> q.index("product")
                    .query(querySupplier.get()), ProductES.class);

            log.info("Elasticsearch response: {}", response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return extractItemsFromResponse(response);
    }


    @Override
    @Transactional
    public ProductResponse updateProductById(Long sellerId, UUID productId, UpdateProductRequest request) {

        sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + sellerId));

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        log.info("ProductServiceImpl.updateProductById product: {}", product);
        return ProductMapper.mapToProductResponse(productRepository.save(ProductMapper.mapToProduct(product, request)));
    }


    @Override
    @Transactional
    public String deleteProductById(Long sellerId, UUID productId) {

        sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + sellerId));

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        productRepository.delete(product);
        return "Product deleted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<ProductResponse> searchProductsByKeyword(String keyword, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.searchByKeyword(keyword, pageable);

        log.info("ProductServiceImpl.searchProductsByKeyword productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<ProductResponse> findAllProducts(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.findAll(pageable);

        log.info("ProductServiceImpl.findAllProducts productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findProductById(UUID productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        log.info("ProductServiceImpl.findProductById product: {}", product);
        return ProductMapper.mapToProductResponse(product);

    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<ProductResponse> findAllProductsByPriceDesc(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.findAllByPriceDesc(pageable);

        log.info("ProductServiceImpl.findAllProductsByPriceDesc productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));

    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<ProductResponse> findAllProductsByPriceAsc(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.findAllByPriceAsc(pageable);

        log.info("ProductServiceImpl.findAllProductsByPriceAsc productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));

    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<ProductResponse> findAllProductsByCreatedDateDesc(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.findAllByCreatedDateDesc(pageable);

        log.info("ProductServiceImpl.findAllProductsByCreatedDateDesc productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<ProductResponse> findAllProductsBySellerId(Long sellerId, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.findAllBySellerId(sellerId, pageable);

        log.info("ProductServiceImpl.findAllProductsBySellerId productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));

    }

    @Override
    public CustomPageResponse<ProductResponse> findAllProductsByBlackList(Long userId, String keyword, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Product> productPage = productRepository.findAllByNonBlackListedSellersAndKeyword(userId, keyword, pageable);

        log.info("ProductServiceImpl.findAllProductsByBlackList productPage: {}", productPage);
        return CustomPageResponse.of(productPage.map(ProductMapper::mapToProductResponse));
    }

    public List<ProductES> extractItemsFromResponse(SearchResponse<ProductES> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }


}
