package com.obss_final_project.e_commerce.service;

import com.obss_final_project.e_commerce.model.Role;

import java.util.List;


public interface RoleService {

    List<Role> saveAllRoles(String... roleNames);

    List<Role> findAllByNameIn(String... roleNames);

}
