package com.obss_final_project.e_commerce.service;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.request.user.CreateUserRequest;
import com.obss_final_project.e_commerce.dto.request.user.UpdateUserRequest;
import com.obss_final_project.e_commerce.dto.response.user.UserResponse;

public interface UserService {


    CustomPageResponse<UserResponse> searchUsersByKeyword(String request, int pageNumber, int pageSize);

    void saveAdmin();

    UserResponse saveUser(CreateUserRequest request);

    UserResponse updateUserById(Long id, UpdateUserRequest request);

    String deleteUserById(Long id);

    UserResponse findUserById(Long id);

    UserResponse getLoggedInUser();

    CustomPageResponse<UserResponse> findAllUsers(int pageNumber, int pageSize);
}
