package com.obss_final_project.e_commerce.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.obss_final_project.e_commerce.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private User user;
    private String message;
    private Long id;
}
