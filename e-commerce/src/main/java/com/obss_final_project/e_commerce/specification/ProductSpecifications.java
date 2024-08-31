package com.obss_final_project.e_commerce.specification;

import com.obss_final_project.e_commerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> containsKeyword(String keyword) {
        return (root, query, builder) -> {
            String likePattern = "%" + keyword.toLowerCase() + "%";

            return builder.or(
                    builder.like(builder.lower(root.get("name")), likePattern)
            );
        };
    }

}
