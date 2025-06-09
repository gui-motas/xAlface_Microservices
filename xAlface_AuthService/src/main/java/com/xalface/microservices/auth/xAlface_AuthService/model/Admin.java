package com.xalface.microservices.auth.xAlface_AuthService.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import lombok.*;


@Getter @Setter

public class Admin extends User {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
       return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
