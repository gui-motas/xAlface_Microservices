package com.xAlface.microservices.user.xAlface_UserService.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xAlface.microservices.user.xAlface_UserService.model.Admin;
import com.xAlface.microservices.user.xAlface_UserService.services.AdminService;

@RestController
@RequestMapping(value = "/user/admin")
public class AdminController {

    @Autowired
    private AdminService adminServ;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Admin obj = adminServ.findById(id);
            if (obj == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro: Administrador com id " + id + " não encontrado.");
            }
            return ResponseEntity.ok().body(obj);
        } catch (RuntimeException e) { // Use a more specific exception if one is known
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar o administrador: " + e.getMessage() + 
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            Admin obj = adminServ.findByUsername(username);
            if (obj == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro: Administrador com username " + username + " não encontrado.");
            }
            return ResponseEntity.ok().body(obj);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar o administrador: " + e.getMessage() + 
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        try {
            List<Admin> list = adminServ.findAll();
            return ResponseEntity.ok().body(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar a lista de administradores: " + e.getMessage() + 
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Admin admin) {
        try {
            Admin createdAdmin = adminServ.create(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar administrador: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar administrador: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            adminServ.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o administrador: " + e.getMessage() + 
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @PutMapping(value = "/{id}/updateUsername")
    public ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody String username) {
        try {
            Admin obj = adminServ.updateUsername(id, username);
            return ResponseEntity.ok().body(obj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o username: " + e.getMessage() + 
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @PutMapping(value = "/{id}/updatePassword")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String password) {
        try {
            Admin obj = adminServ.updatePassword(id, password);
            return ResponseEntity.ok().body(obj);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar a senha: " + e.getMessage() + 
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }
}