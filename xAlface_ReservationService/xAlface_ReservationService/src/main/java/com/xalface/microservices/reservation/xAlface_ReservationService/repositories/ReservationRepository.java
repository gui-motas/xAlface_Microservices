package com.xalface.microservices.reservation.xAlface_ReservationService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xalface.microservices.reservation.xAlface_ReservationService.model.Reservation;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByTeacherId(Long teacherId);
    List<Reservation> findByClassroomId(Long classroomId);
}
