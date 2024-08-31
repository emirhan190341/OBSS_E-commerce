package com.obss_final_project.e_commerce.mapper;

import com.obss_final_project.e_commerce.dto.request.seller.CreateSellerRequest;
import com.obss_final_project.e_commerce.dto.request.seller.UpdateSellerRequest;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import com.obss_final_project.e_commerce.model.Seller;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerMapper {


    public Seller mapInputToEntity(CreateSellerRequest input) {
        Seller seller = new Seller();
        seller.setCompanyName(input.getCompanyName());
        seller.setContactNumber(input.getContactNumber());
        seller.setEmail(input.getEmail());
        seller.setWebsiteUrl(input.getWebsiteUrl());
        seller.setAddress(input.getAddress());
        seller.setRating(input.getRating());
        seller.setIsVerified(input.getIsVerified());

        return seller;
    }

    public SellerResponse mapEntityToResponse(Seller seller) {

        SellerResponse sellerResponse = new SellerResponse();
        sellerResponse.setId(seller.getId());
        sellerResponse.setCompanyName(seller.getCompanyName());
        sellerResponse.setContactNumber(seller.getContactNumber());
        sellerResponse.setLogo(seller.getLogo());
        sellerResponse.setEmail(seller.getEmail());
        sellerResponse.setWebsiteUrl(seller.getWebsiteUrl());
        sellerResponse.setAddress(seller.getAddress());
        sellerResponse.setRating(seller.getRating());
        sellerResponse.setIsVerified(seller.getIsVerified());

        return sellerResponse;
    }

    public Seller updateEntity(Seller seller, UpdateSellerRequest request) {
        if (request.getCompanyName() != null) {
            seller.setCompanyName(request.getCompanyName());
        }

        if (request.getContactNumber() != null) {
            seller.setContactNumber(request.getContactNumber());
        }

        if (request.getLogo() != null) {
            seller.setLogo(request.getLogo());
        }

        if (request.getWebsiteUrl() != null) {
            seller.setWebsiteUrl(request.getWebsiteUrl());
        }

        if (request.getAddress() != null) {
            seller.setAddress(request.getAddress());
        }

        if (request.getRating() != null) {
            seller.setRating(request.getRating());
        }

        if (request.getIsVerified() != null) {
            seller.setIsVerified(request.getIsVerified());
        }

        return seller;
    }

}
