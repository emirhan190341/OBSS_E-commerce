package com.obss_final_project.e_commerce.service.impl;

import com.obss_final_project.e_commerce.model.Role;
import com.obss_final_project.e_commerce.repository.RoleRepository;
import com.obss_final_project.e_commerce.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@Transactional
class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> saveAllRoles(String... roleNames) {
        List<Role> roles = Arrays.stream(roleNames).map(Role::new).toList();

        log.info("Saving roles: {}", roles);
        return roleRepository.saveAll(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllByNameIn(String... roleNames) {

        log.info("Finding roles by names: {}", Arrays.toString(roleNames));
        return roleRepository.findAllByNameIn(Arrays.asList(roleNames));
    }
}
