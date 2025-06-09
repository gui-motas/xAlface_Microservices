package com.xalface.microservices.reservation.xAlface_ReservationService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ClassroomDTO;

@FeignClient("xalface-classroomservice")

public interface ClassroomClient {
    
    @GetMapping("/classrooms/{id}")
    ClassroomDTO getById(@PathVariable Long id);

    @GetMapping("/classrooms/roomCode/{roomCode}")
    ClassroomDTO getByRoomCode(@PathVariable String roomCode);

}
