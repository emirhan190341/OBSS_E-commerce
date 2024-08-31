package com.obss_final_project.e_commerce.dto.request.seller;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateSellerRequest implements Serializable {

    private String companyName;
    private String contactNumber;
    private String logo;
    private String websiteUrl;
    private String address;
    private Double rating;
    private Boolean isVerified;



}
