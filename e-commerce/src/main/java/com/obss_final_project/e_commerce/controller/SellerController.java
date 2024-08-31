package com.obss_final_project.e_commerce.controller;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.CustomResponse;
import com.obss_final_project.e_commerce.dto.request.seller.CreateSellerRequest;
import com.obss_final_project.e_commerce.dto.request.seller.UpdateSellerRequest;
import com.obss_final_project.e_commerce.dto.response.seller.SellerResponse;
import com.obss_final_project.e_commerce.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/sellers")
@Slf4j
@Tag(name = "Seller Controller", description = "API for seller management operations")
class SellerController {

    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "6";

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Operation(
            summary = "Save Seller with Admin Role",
            description = "Save seller",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seller saved successfully",
                    content = @Content(schema = @Schema(implementation = SellerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<SellerResponse> saveSeller(@RequestPart @Valid CreateSellerRequest request,
                                                     @RequestPart MultipartFile file) {

        log.info("SellerController.createSeller request: {}", request);
        return CustomResponse.created(sellerService.saveSeller(request, file));
    }

    @Operation(
            summary = "Update Seller with Admin Role",
            description = "Update seller by id",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller updated successfully",
                    content = @Content(schema = @Schema(implementation = SellerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CustomResponse<SellerResponse> updateSellerById(@PathVariable Long id,
                                                           @RequestBody @Valid UpdateSellerRequest request) {

        log.info("SellerController.updateSeller request: {}", request);
        return CustomResponse.ok(sellerService.updateSellerById(id, request));
    }

    @Operation(
            summary = "Delete Seller with Admin Role",
            description = "Delete seller by id",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public CustomResponse<String> deleteSellerById(@PathVariable Long id) {

        log.warn("SellerController.deleteSellerById id: {}", id);
        return CustomResponse.ok(sellerService.deleteSellerById(id));
    }

    @Operation(
            summary = "Get Seller by Id",
            description = "Get seller by id",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seller found successfully",
                    content = @Content(schema = @Schema(implementation = SellerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/{id}")
    public CustomResponse<SellerResponse> findSellerById(@PathVariable Long id) {

        log.info("SellerController.findSellerById id: {}", id);
        return CustomResponse.ok(sellerService.findSellerById(id));
    }

    @Operation(
            summary = "Get All Sellers",
            description = "Get all sellers",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sellers found successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping
    public CustomResponse<CustomPageResponse<SellerResponse>> findAllSellers(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER ) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("SellerController.findAllSellers page: {}, size: {}", pageNumber, pageSize);
        return CustomResponse.ok(sellerService.findAllSellers(pageNumber, pageSize));
    }


    @Operation(
            summary = "Search Sellers by Company Name",
            description = "Search sellers by company name",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sellers found successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/search")
    public CustomResponse<CustomPageResponse<SellerResponse>> searchSellersByCompanyName(@RequestParam String keyword,
                                                                                         @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                                                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size) {

        log.info("SellerController.searchSellers search: {}", keyword);
        return CustomResponse.ok(sellerService.searchSellersByCompanyName(keyword, page, size));
    }

    @Operation(
            summary = "Get Sellers by Rating Descending",
            description = "Get sellers by rating descending",
            tags = {"Seller Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sellers found successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/rating")
    public CustomResponse<CustomPageResponse<SellerResponse>> findAllSellersByRatingDesc(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("SellerController.findAllSellersByRatingDesc page: {}, size: {}", pageNumber, pageSize);
        return CustomResponse.ok(sellerService.findAllSellersByRatingDesc(pageNumber, pageSize));
    }

}
