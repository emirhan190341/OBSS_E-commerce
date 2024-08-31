package com.obss_final_project.e_commerce.exception.seller;

import com.obss_final_project.e_commerce.exception.NotFoundException;

public class SellerNotFoundException extends NotFoundException {
    public SellerNotFoundException(String message) {
        super(message);
    }
}
