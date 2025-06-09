package com.xalface.microservices.reservation.xAlface_ReservationService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.TeacherDTO;

@FeignClient("xalface-userservice")
public interface TeacherClient {

    @GetMapping("/user/teacher/{id}")
    TeacherDTO getById(@PathVariable Long id);
}
