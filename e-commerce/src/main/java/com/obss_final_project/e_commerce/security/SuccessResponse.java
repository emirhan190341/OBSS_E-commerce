package com.obss_final_project.e_commerce.security;

import com.obss_final_project.e_commerce.dto.response.user.UserResponse;
import lombok.Data;

@Data
public class SuccessResponse {
    private final String message;
    private final UserResponse user;

    public SuccessResponse(String message, UserResponse user) {
        this.message = message;
        this.user = user;
    }
}
