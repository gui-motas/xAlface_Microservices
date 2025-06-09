package com.xalface.microservices.auth.xAlface_AuthService.controllers;

import com.xalface.microservices.auth.xAlface_AuthService.clients.UserServiceClient;
import com.xalface.microservices.auth.xAlface_AuthService.model.AdminDTO;
import com.xalface.microservices.auth.xAlface_AuthService.model.LoginDTO;
import com.xalface.microservices.auth.xAlface_AuthService.model.RegisterDTO;
import com.xalface.microservices.auth.xAlface_AuthService.model.TeacherDTO;
import com.xalface.microservices.auth.xAlface_AuthService.producers.RegisterProducer;
import com.xalface.microservices.auth.xAlface_AuthService.services.AuthenticationService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {
    
    @Autowired
    RegisterProducer registerProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserServiceClient userServiceClient;

    


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                );
            
            Authentication authentication = authenticationManager.authenticate(authToken);
            String token = authenticationService.authenticate(authentication);
            
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro: Credenciais inválidas. Verifique seu nome de usuário e senha.");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro de autenticação: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar o login: " + e.getMessage() +
                            " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO credentials) {
        try {
            // Verifica se o username já existe como admin
            try {
                AdminDTO existingAdmin = this.userServiceClient.findAdminByUsername(credentials.getUsername());
                if (existingAdmin != null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Erro: Username já existe");
                }
            } catch (FeignException.NotFound e) {
                
            }

            // Verifica se o username já existe como teacher
            try {
                TeacherDTO existingTeacher = this.userServiceClient.findTeacherByUsername(credentials.getUsername());
                if (existingTeacher != null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Erro: Username já existe");
                }
            } catch (FeignException.NotFound e) {
                
            }

            String encryptedPassword = passwordEncoder.encode(credentials.getPassword());
            credentials.setPassword(encryptedPassword);

            if (credentials.getRole().equals("ROLE_TEACHER")) {
                TeacherDTO teacher = new TeacherDTO();
                teacher.setName(credentials.getName());
                teacher.setUsername(credentials.getUsername());
                teacher.setPassword(credentials.getPassword());
                teacher.setDepartment(credentials.getDepartment());

                TeacherDTO savedTeacher = userServiceClient.saveTeacher(teacher);

                // Envia a mensagem para o RabbitMQ
                registerProducer.sendMessage(credentials);
                return ResponseEntity.ok().body("Professor registrado com sucesso: " + savedTeacher.getName());

            } else if (credentials.getRole().equals("ROLE_ADMIN")) {
                AdminDTO admin = new AdminDTO();
                admin.setName(credentials.getName());
                admin.setUsername(credentials.getUsername());
                admin.setPassword(credentials.getPassword());

                AdminDTO savedAdmin = userServiceClient.saveAdmin(admin);
                // Envia a mensagem para o RabbitMQ
                registerProducer.sendMessage(credentials);
                return ResponseEntity.ok().body("Administrador registrado com sucesso: " + savedAdmin.getName());

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro: Perfil inválido. Use ROLE_TEACHER ou ROLE_ADMIN.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao registrar usuário: " + e.getMessage() +
                            " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }
}