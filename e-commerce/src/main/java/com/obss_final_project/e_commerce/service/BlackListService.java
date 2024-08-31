package com.obss_final_project.e_commerce.service;

import com.obss_final_project.e_commerce.dto.response.blacklist.BlackListResponse;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import com.obss_final_project.e_commerce.model.BlackList;

import java.util.List;

public interface BlackListService {



    BlackListResponse addSellerToBlackList(Long userId, Long sellerId);


    void removeSellerFromBlackList(Long userId, Long sellerId);


    List<BlackListResponse> getAllBlacklistedSellers(Long userId);

    List<SellerResponse> getAllBlacklistedSellersById(Long userId);
}
