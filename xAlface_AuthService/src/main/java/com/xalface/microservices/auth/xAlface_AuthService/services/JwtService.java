package com.xalface.microservices.auth.xAlface_AuthService.services;

import com.xalface.microservices.auth.xAlface_AuthService.clients.UserServiceClient;
import com.xalface.microservices.auth.xAlface_AuthService.model.AdminDTO;
import com.xalface.microservices.auth.xAlface_AuthService.model.TeacherDTO;

import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    @Autowired
    private UserServiceClient userServiceClient;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        var tokenLifeTime = 3600;

        Instant exp = now.plusSeconds(tokenLifeTime);

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        String username = authentication.getName();

        // Build the claims builder
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("xAlface_auth-microservice")
                .issuedAt(now)
                .expiresAt(exp)
                .subject(authentication.getName())
                .claim("roles", scopes)
                .claim("id", catchUserId(username));

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsBuilder.build())).getTokenValue();
    }

    public Long catchUserId(String username) {
        try {
            TeacherDTO userT = userServiceClient.findTeacherByUsername(username);
            if (userT != null) {
                return userT.getId();
            }
        } catch (FeignException e) {
            // Se não encontrar teacher, tenta admin
        }

        try {
            AdminDTO userA = userServiceClient.findAdminByUsername(username);
            if (userA != null) {
                return userA.getId();
            }
        } catch (FeignException e) {
            // Se não encontrar admin também
        }

        throw new RuntimeException("Usuário não encontrado: " + username);
    }

}
