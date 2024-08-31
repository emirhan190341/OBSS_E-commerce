package com.obss_final_project.e_commerce.exception.user;

import com.obss_final_project.e_commerce.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
