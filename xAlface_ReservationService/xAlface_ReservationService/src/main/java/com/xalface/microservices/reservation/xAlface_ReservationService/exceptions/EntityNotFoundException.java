package com.xalface.microservices.reservation.xAlface_ReservationService.exceptions;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }

}
