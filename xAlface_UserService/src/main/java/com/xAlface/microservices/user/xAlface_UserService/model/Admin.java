package com.xAlface.microservices.user.xAlface_UserService.model;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@Entity
@Table(name = "Admin")
public class Admin extends User {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
       return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
