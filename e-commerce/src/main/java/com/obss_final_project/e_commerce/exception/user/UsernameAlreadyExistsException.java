package com.obss_final_project.e_commerce.exception.user;

import com.obss_final_project.e_commerce.exception.AlreadyException;

public class UsernameAlreadyExistsException extends AlreadyException {
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
