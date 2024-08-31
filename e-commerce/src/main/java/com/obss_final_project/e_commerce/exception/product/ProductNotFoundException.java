package com.obss_final_project.e_commerce.exception.product;

import com.obss_final_project.e_commerce.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
