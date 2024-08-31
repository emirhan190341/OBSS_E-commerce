package com.obss_final_project.e_commerce.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(message);
    }
}
