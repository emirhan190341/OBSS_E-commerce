package com.obss_final_project.e_commerce.specification;

import com.obss_final_project.e_commerce.model.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> containsKeyword(String keyword) {
        return (root, query, builder) -> {
            String likePattern = "%" + keyword.toLowerCase() + "%";

            return builder.or(
                    builder.like(builder.lower(root.get("username")), likePattern),
                    builder.like(builder.lower(root.get("email")), likePattern),
                    builder.like(builder.lower(root.get("name")), likePattern),
                    builder.like(builder.lower(root.get("surname")), likePattern)
            );
        };
    }

}


