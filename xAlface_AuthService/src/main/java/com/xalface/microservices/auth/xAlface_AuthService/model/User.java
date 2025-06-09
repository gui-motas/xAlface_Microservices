package com.xalface.microservices.auth.xAlface_AuthService.model;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.*;


@Getter
@Setter
public abstract class User implements UserDetails {
    
    private Long id;

    private String name;

    private String username;

    private String password;

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
    
}