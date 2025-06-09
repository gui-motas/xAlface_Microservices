package com.xalface.microservices.reservation.xAlface_ReservationService.DTOs;

public record TokenInfo(
    Long id,
    String username,
    String roles
) {}