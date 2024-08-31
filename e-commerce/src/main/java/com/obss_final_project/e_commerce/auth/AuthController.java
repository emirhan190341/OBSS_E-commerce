package com.obss_final_project.e_commerce.auth;

import com.obss_final_project.e_commerce.dto.CustomResponse;
import jakarta.mail.AuthenticationFailedException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody @Valid RegisterRequest registerRequest, HttpServletResponse response) {

        return ResponseEntity
                .ok(authService.register(registerRequest, response));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login (@RequestBody @Valid AuthRequest authRequest, HttpServletResponse response) throws AuthenticationFailedException {

        return ResponseEntity
                .ok(authService.login(authRequest, response));
    }
}
