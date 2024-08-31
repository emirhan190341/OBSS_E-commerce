package com.obss_final_project.e_commerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss_final_project.e_commerce.dto.response.user.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        UserResponse user = new UserResponse();
        user.setUsername(username);
        user.setId(userDetails.getId());
        user.setIsAdmin(userDetails.getIsAdmin());
        user.setEmail(userDetails.getEmail());
        user.setFullName(userDetails.getFullName());
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setAddress(userDetails.getAddress());

        var successResponse = new SuccessResponse("Login successful", user);
        response.getWriter().write(objectMapper.writeValueAsString(successResponse));
    }


}
