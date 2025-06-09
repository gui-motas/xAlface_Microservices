package com.xalface.microservices.reservation.xAlface_ReservationService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ClassroomDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ReservationDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ReservationRequestDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.TokenInfo;
import com.xalface.microservices.reservation.xAlface_ReservationService.clients.AuthClient;
import com.xalface.microservices.reservation.xAlface_ReservationService.clients.ClassroomClient;
import com.xalface.microservices.reservation.xAlface_ReservationService.exceptions.EntityNotFoundException;
import com.xalface.microservices.reservation.xAlface_ReservationService.model.Reservation;
import com.xalface.microservices.reservation.xAlface_ReservationService.repositories.ReservationRepository;

@Service
public class ReservationService {

    
    private ClassroomClient classroomClient;

    private final ReservationRepository reservationRepo;
    private final ValidationService validationService;
   
    
    public ReservationService(ReservationRepository reservationRepo, ValidationService validationService, ClassroomClient classroomClient) {
        this.reservationRepo = reservationRepo;
        this.validationService = validationService;
        this.classroomClient = classroomClient;
    }

    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return reservationRepo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepo.findAll();
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByTeacherId(Long teacherId) {
        return reservationRepo.findByTeacherId(teacherId);
    }
    
    @Autowired
    private AuthClient authClient;

    @Transactional()
    public Reservation create(ReservationRequestDTO dto, String authHeader) {
        TokenInfo tokenInfo = authClient.validateToken(authHeader);
        Long teacherId = tokenInfo.id();
        
        ClassroomDTO classroom = classroomClient.getByRoomCode(dto.roomCode());
        Long classroomId = Long.parseLong(classroom.id()); 
        
        ReservationDTO reservationDTO = new ReservationDTO(
            dto.startDateTime(), 
            dto.endDateTime(), 
            teacherId, 
            classroomId
        );
        
        validationService.validateCreate(reservationDTO);
        
        Reservation reservation = new Reservation();
        reservation.setStartDateTime(reservationDTO.startDateTime());
        reservation.setEndDateTime(reservationDTO.endDateTime());
        reservation.setTeacherId(reservationDTO.teacherId());
        reservation.setClassroomId(reservationDTO.classroomId());
        
        return reservationRepo.save(reservation);
    }

    @Transactional()
    public Reservation update(Long id, ReservationRequestDTO dto, String authHeader) {
        
        TokenInfo tokenInfo = authClient.validateToken(authHeader);
        ClassroomDTO classroom = classroomClient.getByRoomCode(dto.roomCode());
        Long classroomId = Long.parseLong(classroom.id()); 
        Long teacherId = tokenInfo.id();

          ReservationDTO reservationDTO = new ReservationDTO(
            dto.startDateTime(), 
            dto.endDateTime(), 
            teacherId, 
            classroomId
        );
        
        validationService.validateUpdate(reservationDTO, id);

        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada com id: " + id));
        reservation.setStartDateTime(dto.startDateTime());
        reservation.setEndDateTime(dto.endDateTime());
        reservation.setTeacherId(teacherId);
        reservation.setClassroomId(classroomId);


        return reservationRepo.save(reservation);
    }

    @Transactional
    public void delete(Long id) {
        reservationRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Reservation> findByClassroomId(Long classroomId) {
        return reservationRepo.findByClassroomId(classroomId);
    }


     


}
