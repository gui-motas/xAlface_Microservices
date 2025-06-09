package com.xalface.microservices.classroom.xAlface_ClassroomService.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.xalface.microservices.classroom.xAlface_ClassroomService.model.Classroom;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long>{
    Optional<Classroom> findByRoomCode(String roomCode); 
}
