package com.obss_final_project.e_commerce.exception.seller;

import com.obss_final_project.e_commerce.exception.AlreadyException;

public class CompanyNameAlreadyExistsException extends AlreadyException {


    public CompanyNameAlreadyExistsException(String message) {
        super(message);
    }
}
