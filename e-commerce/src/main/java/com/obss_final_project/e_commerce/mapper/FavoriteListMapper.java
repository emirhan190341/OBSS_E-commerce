package com.obss_final_project.e_commerce.mapper;

import com.obss_final_project.e_commerce.dto.response.favoritelist.FavoriteListResponse;
import com.obss_final_project.e_commerce.model.FavoriteList;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FavoriteListMapper {


    public FavoriteListResponse mapToFavoriteListResponse(FavoriteList favoriteList) {

        FavoriteListResponse favoriteListResponse = new FavoriteListResponse();
        favoriteListResponse.setId(favoriteList.getId());
        favoriteListResponse.setUserId(favoriteList.getUser().getId());
        favoriteListResponse.setProductId(favoriteList.getProduct().getId());

        return favoriteListResponse;
    }

}
