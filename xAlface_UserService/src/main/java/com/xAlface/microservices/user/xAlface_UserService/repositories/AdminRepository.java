package com.xAlface.microservices.user.xAlface_UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xAlface.microservices.user.xAlface_UserService.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}