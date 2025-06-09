package com.xalface.microservices.reservation.xAlface_ReservationService.DTOs;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

public record ReservationDTO(
    @NotNull(message = "startDateTime não pode ser nulo")
    @Future(message = "startDateTime deve ser no futuro")
    LocalDateTime startDateTime,

    @NotNull(message = "endDateTime não pode ser nulo")
    @Future(message = "endDateTime deve ser no futuro")
    LocalDateTime endDateTime,

    @NotNull(message = "teacherId não pode ser nulo")
    Long teacherId,

    @NotNull(message = "classroomId não pode ser nulo")
    Long classroomId
    
) {}
