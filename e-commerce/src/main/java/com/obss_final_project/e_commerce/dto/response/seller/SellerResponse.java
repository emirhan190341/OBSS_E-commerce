package com.obss_final_project.e_commerce.dto.response.seller;

import lombok.Data;

import java.io.Serializable;

@Data
public class SellerResponse implements Serializable {

    private Long id;
    private String companyName;
    private String contactNumber;
    private String logo;
    private String email;
    private String websiteUrl;
    private String address;
    private Double rating;
    private Boolean isVerified = false;

}
