package com.obss_final_project.e_commerce.dto.response.favoritelist;

import lombok.Data;

import java.util.UUID;

@Data
public class FavoriteListResponse {
    private Long id;
    private UUID productId;
    private Long userId;

}
