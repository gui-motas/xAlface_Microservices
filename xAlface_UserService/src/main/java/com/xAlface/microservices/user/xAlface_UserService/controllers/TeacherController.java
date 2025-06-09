package com.xAlface.microservices.user.xAlface_UserService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.xAlface.microservices.user.xAlface_UserService.model.Teacher;
import com.xAlface.microservices.user.xAlface_UserService.services.TeacherService;

@RestController
@RequestMapping(value = "/user/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherServ;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Teacher obj = teacherServ.findById(id); 
            if (obj == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro: Professor com id " + id + " não encontrado.");
            }
            return ResponseEntity.ok().body(obj); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar o professor: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        try {
            Teacher obj = teacherServ.findByUsername(username); 
            if (obj == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro: Professor com username " + username + " não encontrado.");
            }
            return ResponseEntity.ok().body(obj); 
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar o professor: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> findAll() {
        try {
            List<Teacher> list = teacherServ.findAll(); 
            return ResponseEntity.ok().body(list);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar a lista de professores: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Teacher teacher) { 
         try {
             Teacher createdTeacher = teacherServ.create(teacher); 
             return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher); 
         } catch (RuntimeException e) { 
             return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar professor: " + e.getMessage());
         } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar professor: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
         }
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createEndpoint(@RequestBody Teacher teacher) { 
         try {
             Teacher createdTeacher = teacherServ.create(teacher); 
             return ResponseEntity.status(HttpStatus.CREATED).body(createdTeacher); 
         } catch (RuntimeException e) { 
             return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro ao criar professor: " + e.getMessage());
         } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar professor: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
         }
    }


    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            teacherServ.delete(id); 
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o professor: " + e.getMessage() +
                          " (Tipo: " + e.getClass().getSimpleName() + ")");
        }
    }

    @PutMapping(value = "/{id}/updateUsername")
    public ResponseEntity<?> updateUsername(@PathVariable Long id, @RequestBody String username) {
        try {
            Teacher obj = teacherServ.updateUsername(id, username); 
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
            Teacher obj = teacherServ.updatePassword(id, password); 
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
