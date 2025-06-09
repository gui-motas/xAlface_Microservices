package com.xalface.microservices.reservation.xAlface_ReservationService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.TokenInfo;

@FeignClient("xalface-authservice")
public interface AuthClient {
    
    @PostMapping("/auth/token/validate")
    TokenInfo validateToken(@RequestBody String token);
}