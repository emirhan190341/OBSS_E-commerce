package com.obss_final_project.e_commerce.exception.user;

import com.obss_final_project.e_commerce.exception.AlreadyException;

public class EmailAlreadyExistsException extends AlreadyException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
