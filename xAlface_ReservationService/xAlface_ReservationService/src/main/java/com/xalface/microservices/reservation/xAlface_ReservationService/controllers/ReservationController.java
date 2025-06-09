package com.xalface.microservices.reservation.xAlface_ReservationService.controllers;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ReservationDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ReservationRequestDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.model.Reservation;
import com.xalface.microservices.reservation.xAlface_ReservationService.service.ReservationService;

@RestController
@RequestMapping(value = "/reservations")
@Validated
public class ReservationController {

    
    private final ReservationService reservationService;

    public ReservationController( ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    //Get all
    @GetMapping
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> list = reservationService.findAll();
        return ResponseEntity.ok(list);
    }

    //Get by id
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        return ResponseEntity.ok(reservation);
    }

    //Get by teacher id
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Reservation>> getByTeacher(@PathVariable Long teacherId) {
        List<Reservation> list = reservationService.findByTeacherId(teacherId);
        return ResponseEntity.ok(list);
    }
    
    //Get by classroom id
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<Reservation>> getByClassroom(@PathVariable Long classroomId) {
        List<Reservation> list = reservationService.findByClassroomId(classroomId);
        return ResponseEntity.ok(list);
    }

    //Create (Post) a new reservation
    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody @Validated ReservationRequestDTO requestDTO, @RequestHeader("Authorization") String authHeader) {
        Reservation reservation = reservationService.create(requestDTO, authHeader);
        return ResponseEntity.status(201).body(reservation);
    }

    //Update (Put) an existing reservation by id
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> update(
            @PathVariable Long id,
            @RequestBody @Validated ReservationRequestDTO dto, @RequestHeader("Authorization") String authHeader) {
        Reservation updated = reservationService.update(id, dto, authHeader);
        return ResponseEntity.ok(updated);
    }

    
    //Delete by id
   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
