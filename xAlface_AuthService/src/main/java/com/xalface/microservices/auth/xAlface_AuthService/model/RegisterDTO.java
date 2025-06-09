package com.xalface.microservices.auth.xAlface_AuthService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    String name;
    String username;
    String password;
    String department;
    String role;
}
