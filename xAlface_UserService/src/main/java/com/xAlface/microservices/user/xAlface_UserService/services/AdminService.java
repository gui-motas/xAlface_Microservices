package com.xAlface.microservices.user.xAlface_UserService.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.xAlface.microservices.user.xAlface_UserService.model.Admin;
import com.xAlface.microservices.user.xAlface_UserService.repositories.AdminRepository;

@Service
public class AdminService {

    @Autowired private AdminRepository repo;


    public Admin findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Admin findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public List<Admin> findAll() {
        return repo.findAll();
    }

    public Admin create(Admin a) {
        return repo.save(a);
    }

    public Admin updateUsername(Long id, String username) {
        Admin a = findById(id);
        if (a == null) {
            throw new IllegalArgumentException("Administrador não encontrado com id: " + id);
        }
        a.setUsername(username);
        return repo.save(a);
    }

    public Admin updatePassword(Long id, String pwd) {
        Admin a = findById(id);
        if (a == null) {
            throw new IllegalArgumentException("Administrador não encontrado com id: " + id);
        }
        a.setPassword(pwd);
        return repo.save(a);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Administrador não encontrado com id: " + id);
        }
        repo.deleteById(id);
    }
}