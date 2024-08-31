package com.obss_final_project.e_commerce.security;


import com.obss_final_project.e_commerce.model.User;
import com.obss_final_project.e_commerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class AuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        AuthUserDetails userDetails = new AuthUserDetails();
        userDetails.setId(user.getId());
        userDetails.setUsername(user.getUsername());
        userDetails.setPassword(user.getPassword());
        userDetails.setEnabled(true);
        userDetails.setIsAdmin(user.getRole().getName().equals("ADMIN"));
        userDetails.setEmail(user.getEmail());
        userDetails.setFullName(user.getName() + " " + user.getSurname());
        userDetails.setPhoneNumber(user.getPhoneNumber());
        userDetails.setAddress(user.getAddress());
        userDetails.setListOfBlacklistedSellers(user.getListOfBlacklistedSellers());
        userDetails.setListOfFavoriteProducts(user.getListOfFavoriteProducts());
        userDetails.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName())));


        return userDetails;
    }
}
