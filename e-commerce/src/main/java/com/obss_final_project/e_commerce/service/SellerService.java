package com.obss_final_project.e_commerce.service;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.request.seller.CreateSellerRequest;
import com.obss_final_project.e_commerce.dto.request.seller.UpdateSellerRequest;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SellerService {
    SellerResponse saveSeller(CreateSellerRequest request, MultipartFile file);

    SellerResponse updateSellerById(Long id, UpdateSellerRequest request);

    String deleteSellerById(Long id);

    CustomPageResponse<SellerResponse> searchSellersByCompanyName(String search, int page, int size);

    SellerResponse findSellerById(Long id);

    CustomPageResponse<SellerResponse> findAllSellers(int pageNumber, int pageSize);

    CustomPageResponse<SellerResponse> findAllSellersByRatingDesc(int pageNumber, int pageSize);
}
