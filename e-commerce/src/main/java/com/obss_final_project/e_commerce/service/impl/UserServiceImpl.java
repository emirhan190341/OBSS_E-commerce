package com.obss_final_project.e_commerce.service.impl;

import com.obss_final_project.e_commerce.dto.CustomPageResponse;
import com.obss_final_project.e_commerce.dto.request.user.CreateUserRequest;
import com.obss_final_project.e_commerce.dto.request.user.UpdateUserRequest;
import com.obss_final_project.e_commerce.dto.response.user.UserResponse;
import com.obss_final_project.e_commerce.exception.user.EmailAlreadyExistsException;
import com.obss_final_project.e_commerce.exception.user.UserNotFoundException;
import com.obss_final_project.e_commerce.exception.user.UsernameAlreadyExistsException;
import com.obss_final_project.e_commerce.mapper.UserMapper;
import com.obss_final_project.e_commerce.model.Role;
import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.UserRepository;
import com.obss_final_project.e_commerce.service.RoleService;
import com.obss_final_project.e_commerce.service.UserService;
import com.obss_final_project.e_commerce.service.rules.UserValidationRules;
import com.obss_final_project.e_commerce.specification.UserSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidationRules userValidationRules;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserMapper userMapper, UserValidationRules userValidationRules) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userValidationRules = userValidationRules;
    }

    @Override
    @Transactional
    public UserResponse saveUser(CreateUserRequest request) {
        User user = userMapper.mapInputToEntity(request);

        userValidationRules.validateUserUniqueness(request.getUsername(), request.getEmail());

        user.setRole(roleService.findAllByNameIn("USER").getFirst());
        user.setIsAdmin(false);

        log.info("userServiceImpl.saveUser() -> user: {}", user.getName());
        return userMapper.mapEntityToResponse(userRepository.save(user));
    }


    @Override
    @Transactional
    public UserResponse updateUserById(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        user = userMapper.updateEntity(user, request);

        log.info("userServiceImpl.updateUser() -> user: {}", user.getName());
        return userMapper.mapEntityToResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public String deleteUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(user);

        log.info("userServiceImpl.deleteUser() -> user: {}", user.getName());
        return "User deleted successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        log.info("userServiceImpl.findUserById() -> user: {}", user.getName());
        return userMapper.mapEntityToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getLoggedInUser() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        log.info("userServiceImpl.getLoggedInUser() -> user: {}", user.getName());
        return userMapper.mapEntityToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<UserResponse> findAllUsers(int pageNumber, int pageSize) {

            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            Page<User> userPage = userRepository.findAll(pageable);

            log.info("userServiceImpl.findAllUsers() -> users: {}", userPage.getContent());
            return CustomPageResponse.of(userPage.map(userMapper::mapEntityToResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomPageResponse<UserResponse> searchUsersByKeyword(String request, int pageNumber, int pageSize) {

        Specification<User> spec = UserSpecifications.containsKeyword(request);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<User> userPage = userRepository.findAll(spec, pageable);

        log.info("userServiceImpl.searchUsersByKeyword() -> users: {}", userPage.getContent());
        return CustomPageResponse.of(userPage.map(userMapper::mapEntityToResponse));
    }


    @Override
    @Transactional
    public void saveAdmin() {

        Map<String, Role> roleMap = roleService.findAllByNameIn("ADMIN", "USER").stream()
                .collect(Collectors.toMap(Role::getName, Function.identity()));

        User userAdmin = new User();
        userAdmin.setUsername("emirhan.admin");
        userAdmin.setPassword(passwordEncoder.encode("User1234"));
        userAdmin.setName("emirhan");
        userAdmin.setSurname("arici");
        userAdmin.setEmail("admin@gmail.com");
        userAdmin.setPhoneNumber("1231231232");
        userAdmin.setAddress("Istanbul");
        userAdmin.setIsAdmin(true);
//        userAdmin.setIsActive(true);

        var adminRole = roleMap.get("ADMIN");
        userAdmin.setRole(adminRole);

        log.info("userServiceImpl.saveAdmin() -> userAdmin: {}", userAdmin.getName());
        userRepository.save(userAdmin);
    }
}


