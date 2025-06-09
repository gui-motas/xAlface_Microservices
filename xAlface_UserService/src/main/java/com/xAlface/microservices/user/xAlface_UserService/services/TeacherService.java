package com.xAlface.microservices.user.xAlface_UserService.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; 

import com.xAlface.microservices.user.xAlface_UserService.model.Teacher;
import com.xAlface.microservices.user.xAlface_UserService.repositories.TeacherRepository; 

@Service 
public class TeacherService {

    @Autowired private TeacherRepository repo; 

    public Teacher findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Teacher findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public List<Teacher> findAll() {
        return repo.findAll();
    }

    public Teacher create(Teacher t) {
        return repo.save(t); 
    }

    public Teacher updateUsername(Long id, String username) {
        Teacher t = findById(id); 
        if (t == null) {
            throw new IllegalArgumentException("Professor não encontrado com id: " + id);
        }
        t.setUsername(username); 
        return repo.save(t); 
    }

    public Teacher updatePassword(Long id, String pwd) {
        Teacher t = findById(id); 
        if (t == null) {
            throw new IllegalArgumentException("Professor não encontrado com id: " + id);
        }
        t.setPassword(pwd);
        return repo.save(t); 
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Professor não encontrado com id: " + id);
        }
        repo.deleteById(id); 
    }
}
