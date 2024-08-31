package com.obss_final_project.e_commerce.service;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.response.cart.CartResponse;
import jakarta.mail.MessagingException;

import java.util.UUID;

public interface CartService {

    CartResponse addProductToCart(Long userId, UUID productId, int quantity);


    CustomPageResponse<CartResponse> getCartByUserId(Long userId, int pageNumber, int pageSize);

    String deleteAllProductsFromCart(Long userId) throws MessagingException;
}
