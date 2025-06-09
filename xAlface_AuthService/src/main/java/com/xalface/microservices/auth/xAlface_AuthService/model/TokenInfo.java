package com.xalface.microservices.auth.xAlface_AuthService.model;

public record TokenInfo(
    Long id,
    String username,
    String roles
) {}