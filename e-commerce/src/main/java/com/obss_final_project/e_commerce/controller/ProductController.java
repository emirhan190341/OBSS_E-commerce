package com.obss_final_project.e_commerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.CustomResponse;
import com.obss_final_project.e_commerce.dto.request.product.CreateProductRequest;
import com.obss_final_project.e_commerce.dto.request.product.UpdateProductRequest;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;
import com.obss_final_project.e_commerce.elasticsearch.ProductES;
import com.obss_final_project.e_commerce.dto.request.product.SearchDtoRequest;
import com.obss_final_project.e_commerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@Tag(name = "Product Controller", description = "Product management operations")
class ProductController {

    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "6";

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @Operation(
            summary = "Save Product with Admin Role",
            description = "Save product",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product saved successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/seller/{sellerId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<ProductResponse> saveProduct(@PathVariable Long sellerId,
                                                       @RequestPart @Valid CreateProductRequest request,
                                                       @RequestPart MultipartFile file) throws JsonProcessingException {

        ProductResponse productResponse = productService.saveProduct(sellerId, request, file);

        log.info("ProductController.saveProduct sellerId: {}, request: {}", sellerId, request);
        return CustomResponse.created(productResponse);
    }

    @Operation(
            summary = "Update Product with Admin Role",
            description = "Update product by id",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/seller/{sellerId}/product/{productId}")
    public CustomResponse<ProductResponse> updateProductById(@PathVariable Long sellerId,
                                                             @PathVariable UUID productId,
                                                             @RequestBody @Valid UpdateProductRequest request) {

        log.info("ProductController.updateProduct sellerId: {}, productId: {}, request: {}", sellerId, productId, request);
        return CustomResponse.ok(productService.updateProductById(sellerId, productId, request));
    }


    @Operation(
            summary = "Delete Product with Admin Role",
            description = "Delete product by id",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/seller/{sellerId}/product/{productId}")
    public CustomResponse<String> deleteProductById(@PathVariable Long sellerId,
                                                    @PathVariable UUID productId) {

        log.info("ProductController.deleteProduct sellerId: {}, productId: {}", sellerId, productId);
        return CustomResponse.ok(productService.deleteProductById(sellerId, productId));
    }


    @Operation(
            summary = "Get All Products",
            description = "Get all products",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping
    public CustomResponse<CustomPageResponse<ProductResponse>> findAllProducts(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all products, pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        return CustomResponse.ok(productService.findAllProducts(pageNumber, pageSize));
    }


    @Operation(
            summary = "Get Product by Id",
            description = "Get product by id",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/{productId}")
    public CustomResponse<ProductResponse> findProductById(@PathVariable UUID productId) {

        log.info("Getting product by id: {}", productId);
        return CustomResponse.ok(productService.findProductById(productId));
    }


    @Operation(
            summary = "Search Products by Keyword",
            description = "Search products by keyword",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/search-all")
    public CustomResponse<CustomPageResponse<ProductResponse>> searchProductsByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Searching users by keyword: {}, pageNumber: {}, pageSize: {}", keyword, pageNumber, pageSize);
        return CustomResponse.ok(productService.searchProductsByKeyword(keyword, pageNumber, pageSize));
    }


    @Operation(
            summary = "Get All Products by Price Descending",
            description = "Get all products by price descending",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/price/desc")
    public CustomResponse<CustomPageResponse<ProductResponse>> findAllProductsByPriceDesc(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
                                                                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all products by price descending");
        return CustomResponse.ok(productService.findAllProductsByPriceDesc(pageNumber, pageSize));
    }


    @Operation(
            summary = "Get All Products by Price Ascending",
            description = "Get all products by price ascending",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/price/asc")
    public CustomResponse<CustomPageResponse<ProductResponse>> findAllProductsByPriceAsc(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
                                                                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all products by price ascending");
        return CustomResponse.ok(productService.findAllProductsByPriceAsc(pageNumber, pageSize));
    }


    @Operation(
            summary = "Get All Products by Created Date Descending",
            description = "Get all products by created date descending",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/createdAt/desc")
    public CustomResponse<CustomPageResponse<ProductResponse>> findAllProductsByCreatedDateDesc(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
                                                                                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all products by created date descending");
        return CustomResponse.ok(productService.findAllProductsByCreatedDateDesc(pageNumber, pageSize));
    }


    @Operation(
            summary = "Get All Products by seller id",
            description = "Get all products",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/seller/{sellerId}")
    public CustomResponse<CustomPageResponse<ProductResponse>> findAllProductsBySellerId(@PathVariable Long sellerId,
                                                                                         @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
                                                                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all products by seller id: {}", sellerId);
        return CustomResponse.ok(productService.findAllProductsBySellerId(sellerId, pageNumber, pageSize));
    }


    @Operation(
            summary = "Get All Products by Blacklist",
            description = "Get all products by blacklist",
            tags = {"Product Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found successfully",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/search")
    public CustomResponse<CustomPageResponse<ProductResponse>> findAllProductsByBlackList(@RequestParam Long userId,
                                                                                          @RequestParam(defaultValue = "") String keyword,
                                                                                          @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
                                                                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all products by blacklist, userId: {}", userId);
        return CustomResponse.ok(productService.findAllProductsByBlackList(userId, keyword, pageNumber, pageSize));
    }

    @GetMapping("/getAllDataFromIndex/{indexName}")
    public ResponseEntity<List<ProductES>> getAllDataFromIndex(@PathVariable String indexName) {
        log.info("Request to get all data from index: {}", indexName);

        return ResponseEntity.ok(productService.getAllDataFromIndex(indexName));
    }

    @GetMapping("/search-es")
    public CustomResponse<List<ProductES>> searchProductsByKeywordES(@RequestBody SearchDtoRequest searchDtoRequest) {
        log.info("Searching products by keyword: {}", searchDtoRequest.getFieldName());

        return CustomResponse.ok(productService.searchProductByFieldAndValue(searchDtoRequest));
    }

}
