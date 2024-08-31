package com.obss_final_project.e_commerce.mapper;

import com.obss_final_project.e_commerce.dto.response.cart.CartResponse;
import com.obss_final_project.e_commerce.model.Cart;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CartMapper {

    public CartResponse toCartResponse(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setProductId(cart.getProduct().getId());
        cartResponse.setProductName(cart.getProduct().getName());
        cartResponse.setImageUrl(cart.getImageUrl());
        cartResponse.setQuantity(cart.getQuantity());
        cartResponse.setPrice(cart.getPrice());

        return cartResponse;

    }
}
