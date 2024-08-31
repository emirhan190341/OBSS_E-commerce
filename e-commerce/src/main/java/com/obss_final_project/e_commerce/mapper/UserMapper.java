package com.obss_final_project.e_commerce.mapper;

import com.obss_final_project.e_commerce.dto.request.user.CreateUserRequest;
import com.obss_final_project.e_commerce.dto.request.user.UpdateUserRequest;
import com.obss_final_project.e_commerce.dto.response.user.UserResponse;
import com.obss_final_project.e_commerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User mapInputToEntity(CreateUserRequest inputDto) {

        User user = new User();
        user.setUsername(inputDto.getUsername());
        user.setPassword(passwordEncoder.encode(inputDto.getPassword()));
        user.setName(inputDto.getName());
        user.setSurname(inputDto.getSurname());
        user.setEmail(inputDto.getEmail());
        user.setPhoneNumber(inputDto.getPhoneNumber());
        user.setAddress(inputDto.getAddress());

        return user;
    }

    public UserResponse mapEntityToResponse(User user) {

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFullName(user.getName() + " " + user.getSurname());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setAddress(user.getAddress());
        userResponse.setIsAdmin(user.getIsAdmin());
        userResponse.setListOfFavoriteProducts(user.getListOfFavoriteProducts());
        userResponse.setListOfBlacklistedSellers(user.getListOfBlacklistedSellers());

        return userResponse;
    }

    public User updateEntity(User user, UpdateUserRequest request) {


        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        return user;
    }


}
