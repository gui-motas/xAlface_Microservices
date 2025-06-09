package com.xalface.microservices.reservation.xAlface_ReservationService.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ClassroomDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.DTOs.ReservationDTO;
import com.xalface.microservices.reservation.xAlface_ReservationService.clients.ClassroomClient;
import com.xalface.microservices.reservation.xAlface_ReservationService.clients.TeacherClient;
import com.xalface.microservices.reservation.xAlface_ReservationService.model.Reservation;
import com.xalface.microservices.reservation.xAlface_ReservationService.repositories.ReservationRepository;

import feign.FeignException;

@Service
public class ValidationService {

    private final ReservationRepository reservationRepo;
    private final TeacherClient teacherClient;
    private final ClassroomClient classroomClient;

    @Autowired
    public ValidationService(ReservationRepository reservationRepo, TeacherClient teacherClient, ClassroomClient classroomClient) {
        this.reservationRepo = reservationRepo;
        this.teacherClient = teacherClient;
        this.classroomClient = classroomClient;
    }

    // Validação para CREATE
    @Transactional(readOnly = true)
    public ReservationDTO validateCreate(ReservationDTO dto) {
        if(!dto.startDateTime().isBefore(dto.endDateTime())){
            throw new RuntimeException("A data e hora de início devem ser anteriores à data e hora de término");
        }

        validateTeacherExists(dto.teacherId());
        validateTeacherConflict(dto);
        validateClassroomAvailability(dto);
        ValidateclassroomConflict(dto);
        return dto;
    }

    // Validação para UPDATE (sobrecarga que recebe o id da reserva, que será ignorada na verificação de conflito)
    public ReservationDTO validateUpdate(ReservationDTO dto, Long updatingReservationId) {
        if(dto.startDateTime().isAfter(dto.endDateTime())){
            throw new RuntimeException("A data e hora de início devem ser anteriores à data e hora de término");
        }
        validateTeacherExists(dto.teacherId());
        validateTeacherConflict(dto, updatingReservationId);
        validateClassroomAvailability(dto);
        validateClassroomConflict(dto, updatingReservationId);
        return dto;
    }

    // ========================
    // 2) MÉTODOS DE VALIDACÃO ATÔMICA
    // ========================


    private void validateTeacherExists(Long teacherId){
        if (teacherId == null) {
            throw new RuntimeException("Campo teacherId não informado.");
        }
        try {
            teacherClient.getById(teacherId);
        } catch (FeignException.NotFound nf) {
            throw new RuntimeException("Professor não encontrado com ID = " + teacherId);
        } catch (FeignException fe) {
            throw new RuntimeException("Erro ao consultar TeacherService: HTTP " 
            + fe.status() + " - " + fe.getMessage());

            
        } catch (Exception e) {
        }
    }

    // Validação de conflito para criação usando o objeto Teacher
    private void validateTeacherConflict(ReservationDTO dto) {
        LocalDateTime startDateTime = dto.startDateTime();
        LocalDateTime endDateTime = dto.endDateTime();

        // Obtém o ID do professor do DTO
        List<Reservation> teacherReservations = reservationRepo.findByTeacherId(dto.teacherId());
        
        for(Reservation existing : teacherReservations) {
            // Verifica se o horário da nova reserva conflita com as reservas existentes do professor
            if (startDateTime.isBefore(existing.getEndDateTime())
                && endDateTime.isAfter(existing.getStartDateTime())) {
                throw new RuntimeException("O professor já possui uma reserva neste período de tempo");
                }
            }
        }

     // Versões sobrecarregadas para update que ignoram a reserva que está sendo atualizada
    private void validateTeacherConflict(ReservationDTO dto, Long updatingReservationId) {
        Long teacherId = dto.teacherId();
        LocalDateTime startDateTime = dto.startDateTime();
        LocalDateTime endDateTime = dto.endDateTime();

        //busca as reservas do professor
        List<Reservation> teacherReservations = reservationRepo.findByTeacherId(teacherId);
        
        // Itera, mas IGNORA a própria reserva que está sendo alterada (updatingReservationId)
         for(Reservation existing : teacherReservations) {
            if (Objects.equals(existing.getId(), updatingReservationId)) {
                continue; // Ignora a própria reserva em atualização
            }
            // Verifica conflito de horário
            if (hasDateTimeConflict(startDateTime, endDateTime, existing)) {
                throw new RuntimeException("Conflito de horário (UPDATE): O professor (Id=" + teacherId +
                ") já possui uma reserva de " 
                + existing.getStartDateTime() + " até " + existing.getEndDateTime() + " neste período");
            }
         }  
    }

    private void validateClassroomAvailability(ReservationDTO dto) {
        Long classroomId = dto.classroomId();

        if (classroomId == null) {
            throw new RuntimeException("ID da sala inválido");
        }

        try {
            ClassroomDTO classroomDto = classroomClient.getById(classroomId);
        
            if (!(classroomDto.available())) {
                throw new RuntimeException("A sala não está disponível");
            }
        
        } catch (FeignException.NotFound nf) {
            throw new RuntimeException("Sala de aula não encontrada com ID = " + classroomId);
        } catch (FeignException fe) {
            throw new RuntimeException("Erro ao consultar ClassroomService: HTTP " 
            + fe.status() + " - " + fe.getMessage());
        }
}

    // Validação de conflito para criação usando o objeto Classroom
    private void ValidateclassroomConflict(ReservationDTO dto) {
        Long classroomId = dto.classroomId();
        LocalDateTime startDateTime = dto.startDateTime();
        LocalDateTime endDateTime = dto.endDateTime();

        List<Reservation> classroomReservations = reservationRepo.findByClassroomId(classroomId);
        
        for(Reservation existing : classroomReservations) {
            if(startDateTime.isBefore (existing.getEndDateTime())
                && endDateTime.isAfter(existing.getStartDateTime())){
                throw new RuntimeException(
                    "Conflito de horário: a sala (ID= " + classroomId +
                    ") já está reservada de " + existing.getStartDateTime() +
                    " até " + existing.getEndDateTime());
            }
        }
    }
    
    private void validateClassroomConflict(ReservationDTO dto, Long updatingReservationId) {
        Long classroomId = dto.classroomId();
        LocalDateTime startDateTime = dto.startDateTime();
        LocalDateTime endDateTime = dto.endDateTime();

        //Busca as reservas da sala
        List<Reservation> classroomReservations = reservationRepo.findByClassroomId(classroomId);

        // Itera, mas IGNORA a própria reserva que está sendo alterada (updatingReservationId)
        for(Reservation existing : classroomReservations) {
            if(Objects.equals(existing.getId(), updatingReservationId)) {
                continue; // Ignora a própria reserva em atualização
            }
            if (hasDateTimeConflict(startDateTime, endDateTime, existing)) {
                throw new RuntimeException(
                    "Conflito de horário (UPDATE): A sala (ID= " + classroomId +
                    ") já está reservada de " + 
                    existing.getStartDateTime() +
                    " até " + existing.getEndDateTime()
                );
            }
        }
    }

    // ========================
    // 3) MÉTODO AUXILIAR
    // ========================

    // Retorna true se [start, end] se sobrepõe ao intervalo de uma reserva existente.
    private boolean hasDateTimeConflict(LocalDateTime startDateTime, LocalDateTime endDateTime, Reservation existing) {
        return startDateTime.isBefore(existing.getEndDateTime())
            && endDateTime.isAfter(existing.getStartDateTime());
    }
}
