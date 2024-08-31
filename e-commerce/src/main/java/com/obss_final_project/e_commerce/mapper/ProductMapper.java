package com.obss_final_project.e_commerce.mapper;

import com.obss_final_project.e_commerce.dto.request.product.CreateProductRequest;
import com.obss_final_project.e_commerce.dto.request.product.UpdateProductRequest;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;
import com.obss_final_project.e_commerce.elasticsearch.ProductES;
import com.obss_final_project.e_commerce.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {


    public Product mapToProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
//        product.setLogo(request.getLogo());
        product.setCategories(request.getCategories());

        return product;
    }


    public ProductResponse mapToProductResponse(Product input) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(input.getId());
        productResponse.setName(input.getName());
        productResponse.setDescription(input.getDescription());
        productResponse.setPrice(input.getPrice());
        productResponse.setQuantity(input.getQuantity());
        productResponse.setLogo(input.getLogo());
        productResponse.setSellerId(input.getSeller().getId());
        productResponse.setCategories(input.getCategories());

        return productResponse;
    }

    public Product mapToProduct(Product product, UpdateProductRequest request) {

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getQuantity() != 0) {
            product.setQuantity(request.getQuantity());
        }

        if (request.getLogo() != null) {
            product.setLogo(request.getLogo());
        }

        if (request.getCategories() != null) {
            product.setCategories(request.getCategories());
        }
        return product;
    }


    public static ProductES mapToProductES(Product product) {
        ProductES productEs = new ProductES();
        productEs.setId(product.getId().toString());
        productEs.setName(product.getName());
        productEs.setDescription(product.getDescription());
        productEs.setPrice(product.getPrice());
        productEs.setQuantity(product.getQuantity());
        productEs.setLogo(product.getLogo());
        productEs.setCategories(product.getCategories());

        return productEs;
    }

}
