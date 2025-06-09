package com.xalface.microservices.auth.xAlface_AuthService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import com.xalface.microservices.auth.xAlface_AuthService.model.TokenInfo;

@Service
public class TokenValidationService {

    @Autowired
    private JwtDecoder jwtDecoder;

    public TokenInfo validateAndExtractClaims(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        
        return new TokenInfo(
            jwt.getClaim("id"),
            jwt.getClaim("sub"),
            jwt.getClaim("roles")
        );
    }
}