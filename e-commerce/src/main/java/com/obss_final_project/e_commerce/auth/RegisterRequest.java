package com.obss_final_project.e_commerce.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotNull
    @Size(min = 5, max = 15, message = "Username must be between 5 and 15 characters")
    private String username;

    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one digit")
    private String password;

    @NotNull
    @Size(min = 5, max = 20)
    private String name;

    @NotNull
    @Size(min = 5, max = 20)
    private String surname;

    @NotNull
    @Size(min = 5, max = 50)
    private String email;

    @NotNull
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @NotNull
    @Size(min = 10, max = 100)
    private String address;

}
