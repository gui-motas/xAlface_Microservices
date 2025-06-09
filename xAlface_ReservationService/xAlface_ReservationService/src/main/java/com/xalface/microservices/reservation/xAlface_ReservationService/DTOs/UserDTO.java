package com.xalface.microservices.reservation.xAlface_ReservationService.DTOs;

public record UserDTO(
    Long id,
    String name,
    String username,
    String password
) {}


