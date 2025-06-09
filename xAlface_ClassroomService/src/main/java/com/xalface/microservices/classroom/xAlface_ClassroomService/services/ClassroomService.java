package com.xalface.microservices.classroom.xAlface_ClassroomService.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xalface.microservices.classroom.xAlface_ClassroomService.model.Classroom;
import com.xalface.microservices.classroom.xAlface_ClassroomService.repositories.ClassroomRepository;
import com.xalface.microservices.classroom.xAlface_ClassroomService.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClassroomService {

    @Autowired
    private ClassroomRepository classroomRepo;

    public Classroom findById(Long id) {
        return classroomRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Classroom findByRoomCode(String roomCode) {
        return classroomRepo.findByRoomCode(roomCode).orElseThrow(() -> new ResourceNotFoundException("Room code not found: " + roomCode));
    }

    public List<Classroom> findAll() {
        return classroomRepo.findAll();
    }

    public Classroom insert(Classroom obj) {
        return classroomRepo.save(obj);
    }

    public Classroom update(Long id, Classroom obj) {
        try {
            Classroom entity = classroomRepo.getReferenceById(id);

            entity.setName(obj.getName());
            entity.setRoomCode(obj.getRoomCode());
            entity.setCapacity(obj.getCapacity());
            entity.setAvailable(obj.isAvailable());

            return classroomRepo.save(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void toggleAvailability(Long id) {
        Classroom entity = findById(id);
        entity.setAvailable(!entity.isAvailable());
        classroomRepo.save(entity);
    }

    public void delete(Long id) {
        classroomRepo.deleteById(id);
    }
} 
