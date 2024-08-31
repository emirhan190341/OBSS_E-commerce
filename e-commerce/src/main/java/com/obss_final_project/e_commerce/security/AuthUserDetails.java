package com.obss_final_project.e_commerce.security;

import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
public class AuthUserDetails implements UserDetails {

    @Getter
    private Long id;
    @Getter
    private Boolean isAdmin;
    @Getter
    private String email;
    @Getter
    private String fullName;
    @Getter
    private String phoneNumber;
    @Getter
    private String address;
    private String username;
    @Getter
    private Set<Long> listOfBlacklistedSellers = new HashSet<>();
    @Getter
    private Set<UUID> listOfFavoriteProducts = new HashSet<>();
    private String password;
    private boolean isEnabled;
    private List<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnabled;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }


}
