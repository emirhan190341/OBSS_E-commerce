package com.obss_final_project.e_commerce.exception;

import org.springframework.http.HttpStatus;

public class AlreadyException extends RuntimeException{

    public static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public AlreadyException(String message) {
        super(message);
    }

}
