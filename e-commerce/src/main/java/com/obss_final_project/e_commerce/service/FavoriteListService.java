package com.obss_final_project.e_commerce.service;

import com.obss_final_project.e_commerce.dto.response.favoritelist.FavoriteListResponse;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface FavoriteListService {
    FavoriteListResponse addProductToFavoriteList(Long userId, UUID productId);

    void removeProductFromFavoriteList(Long userId, UUID productId);

    List<ProductResponse> getFavoriteListByUserId(Long userId);
}
