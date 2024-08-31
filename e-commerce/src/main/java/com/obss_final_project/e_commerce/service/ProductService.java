package com.obss_final_project.e_commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.request.product.CreateProductRequest;
import com.obss_final_project.e_commerce.dto.request.product.UpdateProductRequest;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;
import com.obss_final_project.e_commerce.elasticsearch.ProductES;
import com.obss_final_project.e_commerce.dto.request.product.SearchDtoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse saveProduct(Long sellerId, CreateProductRequest request,MultipartFile file) throws JsonProcessingException;


    List<ProductES> getAllDataFromIndex(String indexName);

    List<ProductES> searchProductByFieldAndValue(SearchDtoRequest dto);

    ProductResponse updateProductById(Long sellerId, UUID productId, UpdateProductRequest request);

    String deleteProductById(Long sellerId, UUID productId);

    CustomPageResponse<ProductResponse> searchProductsByKeyword(String keyword, int pageNumber, int pageSize);

    CustomPageResponse<ProductResponse> findAllProducts(int pageNumber, int pageSize);

    ProductResponse findProductById(UUID productId);

    CustomPageResponse<ProductResponse> findAllProductsByPriceDesc(int pageNumber, int pageSize);

    CustomPageResponse<ProductResponse> findAllProductsByPriceAsc(int pageNumber, int pageSize);

    CustomPageResponse<ProductResponse> findAllProductsByCreatedDateDesc(int pageNumber, int pageSize);

    CustomPageResponse<ProductResponse> findAllProductsBySellerId(Long sellerId, int pageNumber, int pageSize);

    CustomPageResponse<ProductResponse> findAllProductsByBlackList(Long userId,String keyword, int pageNumber, int pageSize);


}
