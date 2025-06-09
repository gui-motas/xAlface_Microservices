package com.xalface.microservices.auth.xAlface_AuthService.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TeacherDTO  {
    Long id;
    @Setter
    String username;
    @Setter
    String name;
    @Setter
    String password;
    @Setter
    String email;
    @Setter
    String role;
    @Setter
    String department;
}
