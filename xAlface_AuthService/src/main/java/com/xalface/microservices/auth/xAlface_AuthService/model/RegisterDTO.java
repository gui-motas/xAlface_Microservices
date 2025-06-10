package com.xalface.microservices.auth.xAlface_AuthService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    String name;
    String username;
    String password;
    String department;
    String role;
}
