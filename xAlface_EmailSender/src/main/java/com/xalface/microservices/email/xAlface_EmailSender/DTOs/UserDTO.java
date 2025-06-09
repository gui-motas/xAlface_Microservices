package com.xalface.microservices.email.xAlface_EmailSender.DTOs;

public record UserDTO(
    String name,
    String username,
    String password,
    String role,
    String department
) {

}
