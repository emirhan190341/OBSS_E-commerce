package com.obss_final_project.e_commerce.dto.request.seller;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateSellerRequest implements Serializable {


    @NotNull
    @Size(min = 5, max = 15, message = "Company must be between 5 and 15 characters")
    private String companyName;

    @NotNull
    @Size(min = 10, max = 15)
    private String contactNumber;

    @NotNull
    @Size(min = 5, max = 50)
    private String email;

    private String websiteUrl;

    private String address;

    private Double rating;

    private Boolean isVerified;



}
