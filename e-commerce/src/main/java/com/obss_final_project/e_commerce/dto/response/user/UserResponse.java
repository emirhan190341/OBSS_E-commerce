package com.obss_final_project.e_commerce.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.obss_final_project.e_commerce.model.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class UserResponse implements Serializable {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean isAdmin;
    private Set<Long> listOfBlacklistedSellers = new HashSet<>();
    private Set<UUID> listOfFavoriteProducts = new HashSet<>();

}

