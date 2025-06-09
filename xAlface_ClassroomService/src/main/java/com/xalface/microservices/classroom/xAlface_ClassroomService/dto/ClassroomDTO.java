package com.xalface.microservices.classroom.xAlface_ClassroomService.dto;

public record ClassroomDTO(
    String id,
    String roomCode,
    String name,
    String description,
    int capacity,
    boolean isAvailable
) {}
