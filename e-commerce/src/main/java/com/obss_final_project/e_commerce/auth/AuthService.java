package com.obss_final_project.e_commerce.auth;

import com.obss_final_project.e_commerce.exception.user.EmailAlreadyExistsException;
import com.obss_final_project.e_commerce.model.Role;
import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.RoleRepository;
import com.obss_final_project.e_commerce.repository.UserRepository;
import com.obss_final_project.e_commerce.security.JwtService;
import jakarta.mail.AuthenticationFailedException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt-cookieExpiry}")
    private int cookieExpiry;


    public AuthService(UserRepository userRepository,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest, HttpServletResponse response) {

        Role userRole = roleRepository.findByName("USER");

        User user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .address(registerRequest.getAddress())
                .role(userRole)
                .build();

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("User with email already exists. Please login instead or use a different email address.");
        }

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);

        setCookie(response, jwtToken);

        return AuthResponse.builder()
                .user(savedUser)
                .message("User registered successfully")
                .id(savedUser.getId())
                .build();


    }

    @Transactional
    public AuthResponse login(AuthRequest request, HttpServletResponse response) throws AuthenticationFailedException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow();

            var jwtToken = jwtService.generateToken(user);

            setCookie(response, jwtToken);

            return AuthResponse.builder()
                    .user(user)
                    .message("User authenticated successfully")
                    .build();

        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Authentication failed");
        }
    }

    public AuthResponse logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("None")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return AuthResponse.builder()
                .message("User logged out successfully")
                .build();
    }

    private void setCookie(HttpServletResponse response, String jwtToken) {
        ResponseCookie cookie = ResponseCookie.from("accessToken", jwtToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(cookieExpiry)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

}
