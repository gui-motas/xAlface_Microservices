package com.xAlface.microservices.user.xAlface_UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xAlface.microservices.user.xAlface_UserService.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByUsername(String username);
}