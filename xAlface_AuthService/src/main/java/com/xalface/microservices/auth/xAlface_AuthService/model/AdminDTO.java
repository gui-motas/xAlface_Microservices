package com.xalface.microservices.auth.xAlface_AuthService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AdminDTO  {

    Long id;
    @Setter
    String username;
    @Setter
    String name;
    @Setter
    String password;
    @Setter
    String role;

    
}
