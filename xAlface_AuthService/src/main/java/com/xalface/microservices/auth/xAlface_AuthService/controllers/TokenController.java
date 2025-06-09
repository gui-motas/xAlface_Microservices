package com.xalface.microservices.auth.xAlface_AuthService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.xalface.microservices.auth.xAlface_AuthService.services.TokenValidationService;
import com.xalface.microservices.auth.xAlface_AuthService.model.TokenInfo;

@RestController
@RequestMapping("/auth/token")
public class TokenController {

    @Autowired
    private TokenValidationService tokenValidationService;

    @PostMapping("/validate")
    public ResponseEntity<TokenInfo> validateToken(@RequestBody String token) {
        try {
            // Remove "Bearer " se presente
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            TokenInfo tokenInfo = tokenValidationService.validateAndExtractClaims(token);
            return ResponseEntity.ok(tokenInfo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}