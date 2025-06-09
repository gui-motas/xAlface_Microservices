package com.xalface.microservices.classroom.xAlface_ClassroomService.controllers;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xalface.microservices.classroom.xAlface_ClassroomService.dto.ClassroomDTO;
import com.xalface.microservices.classroom.xAlface_ClassroomService.model.Classroom;
import com.xalface.microservices.classroom.xAlface_ClassroomService.service.ReservationService;

@RestController
@RequestMapping(value = "/classrooms")
@Validated
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/{id}")
    @Operation(summary = "Get classroom by ID", description = "Retrieves a classroom by its ID from the database")
    @ApiResponse(responseCode = "200", description = "Classroom found")
    public ResponseEntity<Classroom> findById(@PathVariable Long id) {
        Classroom obj = classroomService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/roomCode/{roomCode}")
    @Operation(summary = "Get classroom by room code", description = "Retrieves a classroom by its unique room code")
    @ApiResponse(responseCode = "200", description = "Classroom found")
    public ResponseEntity<Classroom> findByRoomCode(@PathVariable String roomCode) {
        Classroom obj = classroomService.findByRoomCode(roomCode);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all classrooms", description = "Retrieves all classrooms from the database")
    @ApiResponse(responseCode = "200", description = "All classrooms returned successfully")
    public ResponseEntity<List<Classroom>> findAll() {
        List<Classroom> list = classroomService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    @Operation(summary = "Register a classroom", description = "Creates a new classroom in the database")
    @ApiResponse(responseCode = "201", description = "Classroom has been created")
    public ResponseEntity<Classroom> insert(@RequestBody Classroom obj) {
        obj = classroomService.insert(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update classroom by ID", description = "Updates the details of a classroom by its ID")
    @ApiResponse(responseCode = "200", description = "Classroom has been updated")
    public ResponseEntity<Classroom> update(@PathVariable Long id, @RequestBody Classroom obj) {
        obj = classroomService.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }

    @PutMapping("/{id}/toggle-availability")
    @Operation(summary = "Toggle classroom availability", description = "Switches the availability status of a classroom")
    @ApiResponse(responseCode = "200", description = "Classroom availability has been updated")
    public ResponseEntity<String> toggleAvailability(@PathVariable Long id) {
        classroomService.toggleAvailability(id);
        return ResponseEntity.ok("Classroom availability has been updated.");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete classroom by ID", description = "Deletes a classroom from the database")
    @ApiResponse(responseCode = "204", description = "Classroom has been deleted")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


