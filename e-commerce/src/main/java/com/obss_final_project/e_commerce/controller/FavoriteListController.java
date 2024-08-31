package com.obss_final_project.e_commerce.controller;

import com.obss_final_project.e_commerce.dto.CustomResponse;
import com.obss_final_project.e_commerce.dto.response.blacklist.BlackListResponse;
import com.obss_final_project.e_commerce.dto.response.favoritelist.FavoriteListResponse;
import com.obss_final_project.e_commerce.dto.response.product.ProductResponse;
import com.obss_final_project.e_commerce.service.FavoriteListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/favoritelist")
@Slf4j
@Tag(name = "Favorite List Controller", description = "API for favorite list management operations")
class FavoriteListController {

    private final FavoriteListService favoriteListService;

    @Autowired
    public FavoriteListController(FavoriteListService favoriteListService) {
        this.favoriteListService = favoriteListService;
    }

    @Operation(
            summary = "Add Product to Favorite List",
            description = "Add product to favorite list",
            tags = {"Favorite List Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product added to favorite list",
                    content = @Content(schema = @Schema(implementation = FavoriteListResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<FavoriteListResponse> addProductToFavoriteList(
            @RequestParam Long userId,
            @RequestParam UUID productId) {

        FavoriteListResponse favoriteListResponse = favoriteListService.addProductToFavoriteList(userId, productId);
        return CustomResponse.created(favoriteListResponse);
    }

    @Operation(
            summary = "Remove Product from Favorite List",
            description = "Remove product from favorite list",
            tags = {"Favorite List Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed from favorite list",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/remove")
    public CustomResponse<String> removeProductFromFavoriteList(
            @RequestParam Long userId,
            @RequestParam UUID productId) {

        favoriteListService.removeProductFromFavoriteList(userId, productId);
        return CustomResponse.ok("Product removed from favorite list");
    }

    @Operation(
            summary = "Get Favorite List by User Id",
            description = "Get favorite list by user id",
            tags = {"Favorite List Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite list retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/user/{userId}")
    public CustomResponse<List<ProductResponse>> getFavoriteListByUserId(
            @PathVariable Long userId) {

        List<ProductResponse> favoriteList = favoriteListService.getFavoriteListByUserId(userId);
        return CustomResponse.ok(favoriteList);
    }
}
