package com.obss_final_project.e_commerce.dto.request.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateUserRequest implements Serializable {

    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter and one digit")
    private String password;

    @Size(min = 5, max = 20)
    private String name;

    @Size(min = 5, max = 20)
    private String surname;

    @Size(min = 10, max = 15)
    private String phoneNumber;

    @Size(min = 10, max = 100)
    private String address;

}

