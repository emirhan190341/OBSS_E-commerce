package com.obss_final_project.e_commerce.controller;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.CustomResponse;
import com.obss_final_project.e_commerce.dto.response.cart.CartResponse;
import com.obss_final_project.e_commerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
@Slf4j
@Tag(name = "Cart Controller", description = "Cart management operations")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "Add Product to Cart",
            description = "Add product to cart",
            tags = {"Cart Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added to cart successfully",
                    content = @Content(schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<CartResponse> addProductToCart(@RequestParam Long userId,
                                                         @RequestParam UUID productId,
                                                         @RequestParam int quantity) {

        log.info("Adding product to cart with userId: {} and productId: {}", userId, productId);
        return CustomResponse.created(cartService.addProductToCart(userId, productId, quantity));
    }

    @Operation(
            summary = "Get Cart by User Id",
            description = "Get cart by user id",
            tags = {"Cart Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/get")
    public CustomResponse<CustomPageResponse<CartResponse>> getCartByUserId(@RequestParam Long userId,
                                                                            @RequestParam(defaultValue = "0") int pageNumber,
                                                                            @RequestParam(defaultValue = "6") int pageSize) {

        log.info("Getting cart by userId: {}", userId);
        return CustomResponse.ok(cartService.getCartByUserId(userId,pageNumber,pageSize));
    }

    @Operation(
            summary = "Delete All Products from Cart",
            description = "Delete products from cart",
            tags = {"Cart Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products deleted from cart successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/delete")
    public CustomResponse<String> deleteAllProductsFromCart(@RequestParam Long userId) throws MessagingException {

        log.warn("Deleting all products from cart with userId: {}", userId);
        return CustomResponse.ok(cartService.deleteAllProductsFromCart(userId));
    }

}
