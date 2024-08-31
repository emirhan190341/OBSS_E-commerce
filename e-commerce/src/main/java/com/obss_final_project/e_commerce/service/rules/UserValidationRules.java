package com.obss_final_project.e_commerce.service.rules;

import com.obss_final_project.e_commerce.exception.user.EmailAlreadyExistsException;
import com.obss_final_project.e_commerce.exception.user.UsernameAlreadyExistsException;
import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidationRules {

    private final UserRepository userRepository;

    @Autowired
    public UserValidationRules(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUserUniqueness(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists. Please use a different username.");
        }
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists. Please use a different email.");
        }
    }

    public void validateUserUniquenessForUpdate(User user, String newUsername, String newEmail) {
        if (userRepository.existsByUsername(newUsername) && !user.getUsername().equals(newUsername)) {
            throw new UsernameAlreadyExistsException("Username already exists. Please use a different username.");
        }
        if (userRepository.existsByEmail(newEmail) && !user.getEmail().equals(newEmail)) {
            throw new EmailAlreadyExistsException("Email already exists. Please use a different email.");
        }
    }
}
