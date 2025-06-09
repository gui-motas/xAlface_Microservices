package com.xalface.microservices.auth.xAlface_AuthService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xalface.microservices.auth.xAlface_AuthService.model.AdminDTO;
import com.xalface.microservices.auth.xAlface_AuthService.model.TeacherDTO;

@FeignClient(name = "xalface-userservice")
public interface UserServiceClient {

    @GetMapping("/user/teacher/{id}")
    TeacherDTO findTeacherById(@PathVariable Long id);

    @GetMapping("/user/admin/{id}")
    AdminDTO findAdminById(@PathVariable Long id);

    @GetMapping("/user/teacher/username/{username}")
    TeacherDTO findTeacherByUsername(@PathVariable String username);

    @GetMapping("/user/admin/username/{username}")
    AdminDTO findAdminByUsername(@PathVariable String username);

    @PostMapping("/user/teacher/create")
    TeacherDTO saveTeacher(@RequestBody TeacherDTO obj);

    @PostMapping("/user/admin/create")
    AdminDTO saveAdmin(@RequestBody AdminDTO obj);

}
