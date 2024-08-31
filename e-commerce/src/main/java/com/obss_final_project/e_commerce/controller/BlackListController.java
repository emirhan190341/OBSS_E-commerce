package com.obss_final_project.e_commerce.controller;

import com.obss_final_project.e_commerce.dto.CustomResponse;
import com.obss_final_project.e_commerce.dto.response.blacklist.BlackListResponse;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import com.obss_final_project.e_commerce.service.BlackListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blacklist")
@Slf4j
@Tag(name = "BlackList Controller", description = "Blacklist management operations")
public class BlackListController {

    private final BlackListService blackListService;

    @Autowired
    public BlackListController(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @Operation(
            summary = "Add Seller to Blacklist",
            description = "Add seller to blacklist",
            tags = {"BlackList Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller added to blacklist successfully",
                    content = @Content(schema = @Schema(implementation = BlackListResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/add")
    public CustomResponse<BlackListResponse> addSellerToBlackList(
            @RequestParam Long userId,
            @RequestParam Long sellerId
            ) {

       BlackListResponse blackListResponse = blackListService.addSellerToBlackList(userId, sellerId);
        return CustomResponse.created(blackListResponse);
    }

    @Operation(
            summary = "Remove Seller from Blacklist",
            description = "Remove seller from blacklist",
            tags = {"BlackList Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller removed from blacklist successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @DeleteMapping("/remove")
    public CustomResponse<String> removeSellerFromBlackList(
            @RequestParam Long userId,
            @RequestParam Long sellerId) {
        blackListService.removeSellerFromBlackList(userId, sellerId);
        return CustomResponse.ok("Seller removed from blacklist");
    }

    @Operation(
            summary = "Get All Blacklisted Sellers",
            description = "Get all blacklisted sellers",
            tags = {"BlackList Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All blacklisted sellers retrieved successfully",
                    content = @Content(schema = @Schema(implementation = BlackListResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/user/{userId}")
    public CustomResponse<List<BlackListResponse>> getAllBlacklistedSellers(@PathVariable Long userId) {

        List<BlackListResponse> blackListResponses = blackListService.getAllBlacklistedSellers(userId);
        return CustomResponse.ok(blackListResponses);
    }

    @Operation(
            summary = "Get All Blacklisted Sellers by User Id",
            description = "Get all blacklisted sellers by user id",
            tags = {"BlackList Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All blacklisted sellers retrieved successfully",
                    content = @Content(schema = @Schema(implementation = SellerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping
    public CustomResponse<List<SellerResponse>> getAllBlacklistedSellersById(@RequestParam Long userId) {

        List<SellerResponse> blackListResponses = blackListService.getAllBlacklistedSellersById(userId);
        return CustomResponse.ok(blackListResponses);
    }

}
