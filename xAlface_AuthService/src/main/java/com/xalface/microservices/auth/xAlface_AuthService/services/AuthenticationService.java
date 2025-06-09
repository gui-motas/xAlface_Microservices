package com.xalface.microservices.auth.xAlface_AuthService.services;

import com.xalface.microservices.auth.xAlface_AuthService.model.AdminDTO;
import com.xalface.microservices.auth.xAlface_AuthService.model.TeacherDTO;
import com.xalface.microservices.auth.xAlface_AuthService.clients.UserServiceClient;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentando autenticar usuário: " + username);
        
        // Tenta buscar como admin
        try {
            AdminDTO admin = userServiceClient.findAdminByUsername(username);
            if (admin != null) {
                System.out.println("Usuário encontrado como admin. Senha hash: " + admin.getPassword());
                return User.builder()
                        .username(admin.getUsername())
                        .password(admin.getPassword())
                        .authorities("ROLE_ADMIN")
                        .build();
            }
        } catch (FeignException e) {
            System.out.println("Usuário não encontrado como admin: " + e.getMessage());
        }

        // Tenta buscar como teacher
        try {
            TeacherDTO teacher = userServiceClient.findTeacherByUsername(username);
            if (teacher != null) {
                System.out.println("Usuário encontrado como teacher. Senha hash: " + teacher.getPassword());
                return User.builder()
                        .username(teacher.getUsername())
                        .password(teacher.getPassword())
                        .authorities("ROLE_TEACHER")
                        .build();
            }
        } catch (FeignException e) {
            System.out.println("Usuário não encontrado como teacher: " + e.getMessage());
        }

        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}