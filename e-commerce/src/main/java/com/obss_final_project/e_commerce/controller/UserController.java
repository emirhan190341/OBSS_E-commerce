package com.obss_final_project.e_commerce.controller;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.CustomResponse;
import com.obss_final_project.e_commerce.dto.request.user.CreateUserRequest;
import com.obss_final_project.e_commerce.dto.request.user.UpdateUserRequest;
import com.obss_final_project.e_commerce.dto.response.user.UserResponse;
import com.obss_final_project.e_commerce.service.UserService;
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


@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Tag(name = "User Controller", description = "API for user management operations")
class UserController {

    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "6";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Register a new user)",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User saved successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomResponse<UserResponse> saveUser(@RequestBody @Valid CreateUserRequest request) {

        log.info("Adding user with request: {}", request);
        return CustomResponse.created(userService.saveUser(request));
    }

    @Operation(
            summary = "Update User with Admin Role",
            description = "Update user by id",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    public CustomResponse<UserResponse> updateUserById(@PathVariable Long id,
                                                       @RequestBody @Valid UpdateUserRequest request) {

        log.info("Updating user with id: {} and request: {}", id, request);
        return CustomResponse.ok(userService.updateUserById(id, request));
    }

    @Operation(
            summary = "Delete User with Admin Role",
            description = "Delete user by id",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping({"/{id}"})
    public CustomResponse<String> deleteUserById(@PathVariable Long id) {

        log.warn("Deleting user with id: {}", id);
        return CustomResponse.ok(userService.deleteUserById(id));
    }

    @Operation(
            summary = "Get User by Id with Admin Role",
            description = "Get user by id with admin role",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({"/{id}"})
    public CustomResponse<UserResponse> findUserById(@PathVariable Long id) {

        log.info("Getting user by id: {}", id);
        return CustomResponse.ok(userService.findUserById(id));
    }

    @Operation(
            summary = "Get All Users with Admin Role",
            description = "Get all users with admin role",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public CustomResponse<CustomPageResponse<UserResponse>> findAllUsers(
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Getting all users with pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        return CustomResponse.ok(userService.findAllUsers(pageNumber, pageSize));
    }

    @Operation(
            summary = "Search Users by Keyword with Admin Role",
            description = "Search users by keyword with admin role",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found successfully",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public CustomResponse<CustomPageResponse<UserResponse>> searchUsersByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        log.info("Searching users by keyword: {}, pageNumber: {}, pageSize: {}", keyword, pageNumber, pageSize);
        return CustomResponse.ok(userService.searchUsersByKeyword(keyword, pageNumber, pageSize));
    }

    @Operation(
            summary = "Get Logged In User",
            description = "Get logged in user",
            tags = {"User Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/auth-user")
    public CustomResponse<UserResponse> getLoggedInUser() {

        log.info("Getting logged in user");
        return CustomResponse.ok(userService.getLoggedInUser());
    }
}



