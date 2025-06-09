package com.xalface.microservices.reservation.xAlface_ReservationService.DTOs;

import java.time.LocalDateTime;

public record ReservationRequestDTO(
    LocalDateTime startDateTime,
    LocalDateTime endDateTime,
    String roomCode
)  {}
