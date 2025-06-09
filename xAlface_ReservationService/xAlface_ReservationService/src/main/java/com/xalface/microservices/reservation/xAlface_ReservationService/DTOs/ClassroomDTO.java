package com.xalface.microservices.reservation.xAlface_ReservationService.DTOs;

public record ClassroomDTO(
    String id,
    String roomCode,
    String name,
    String description,
    int capacity,
    boolean available
) {}
