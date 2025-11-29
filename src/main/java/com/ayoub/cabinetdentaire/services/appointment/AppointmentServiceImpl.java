package com.ayoub.cabinetdentaire.services.appointment;

import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentRequest;
import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentResponse;
import com.ayoub.cabinetdentaire.entities.Appointment;
import com.ayoub.cabinetdentaire.enums.AppointmentStatus;
import com.ayoub.cabinetdentaire.mappers.AppointmentMapper;
import com.ayoub.cabinetdentaire.repoositories.AppointmentRepository;
import com.ayoub.cabinetdentaire.services.EntityValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final EntityValidationService validationService;

    // -------------------- CREATE --------------------
    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Appointment appointment = appointmentMapper.toEntity(request);

        appointment.setConsultation(validationService.validateConsultationId(request.getConsultationId()));
        appointment.setPatient(validationService.validatePatientId(request.getPatientId()));
        appointment.setDentist(validationService.validateDentistId(request.getDentistId()));

        appointmentRepository.save(appointment);
        log.info("Created appointment {}", appointment.getId());

        return appointmentMapper.toResponse(appointment);
    }

    // -------------------- READ --------------------
    @Override
    public AppointmentResponse getAppointmentById(UUID id) {
        return appointmentMapper.toResponse(validationService.validateAppointmentId(id));
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatient(UUID patientId) {
        validationService.validatePatientId(patientId);
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDentist(UUID dentistId) {
        validationService.validateDentistId(dentistId);
        return appointmentRepository.findByDentistId(dentistId)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByStatus(AppointmentStatus status) {
        if (status == null) throw new IllegalArgumentException("Status cannot be null");
        return appointmentRepository.findByStatus(status)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(date);
        appointments.sort(Comparator.comparing(Appointment::getAppointmentDateTime));
        return appointments.stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> getUpcomingAppointments() {
        return appointmentRepository.findByAppointmentDateTimeAfterOrderByAppointmentDateTimeAsc(LocalDateTime.now())
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAppointmentDateTimeBetween(start, end)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public long countAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.countByStatus(status);
    }

    // -------------------- UPDATE --------------------
    @Override
    public AppointmentResponse updateAppointment(UUID id, AppointmentRequest request) {
        Appointment appointment = validationService.validateAppointmentId(id);

        checkModifiable(appointment);

        appointmentMapper.updateEntity(appointment, request);

        appointment.setConsultation(validationService.validateConsultationId(request.getConsultationId()));
        appointment.setPatient(validationService.validatePatientId(request.getPatientId()));
        appointment.setDentist(validationService.validateDentistId(request.getDentistId()));

        appointmentRepository.save(appointment);
        log.info("Updated appointment {}", appointment.getId());

        return appointmentMapper.toResponse(appointment);
    }

    @Override
    public AppointmentResponse updateAppointmentStatus(UUID id, AppointmentStatus status) {
        Appointment appointment = validationService.validateAppointmentId(id);

        checkModifiable(appointment);

        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        log.info("Updated status of appointment {} to {}", appointment.getId(), status);

        return appointmentMapper.toResponse(appointment);
    }

    // -------------------- CANCEL --------------------
    @Override
    public void cancelAppointment(UUID id, String reason) {
        Appointment appointment = validationService.validateAppointmentId(id);

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment already cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        if (reason != null && !reason.isBlank()) {
            appointment.setNotes("Cancelled: " + reason);
        }

        appointmentRepository.save(appointment);
        log.info("Cancelled appointment {} with reason: {}", appointment.getId(), reason);
    }

    // -------------------- DELETE --------------------
    @Override
    public void deleteAppointment(UUID id) {
        Appointment appointment = validationService.validateAppointmentId(id);

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot delete a completed appointment");
        }

        appointmentRepository.delete(appointment);
        log.info("Deleted appointment {}", appointment.getId());
    }

    // -------------------- AVAILABILITY --------------------
    @Override
    public boolean isDentistAvailable(UUID dentistId, LocalDateTime startTime, LocalDateTime endTime) {
        return !appointmentRepository.existsByDentistIdAndAppointmentDateTimeLessThanAndEndDateTimeGreaterThan(
                dentistId, endTime, startTime
        );
    }

    // -------------------- PRIVATE --------------------
    private void checkModifiable(Appointment appointment) {
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot modify a cancelled appointment");
        }
        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot modify a completed appointment");
        }
    }


    // -------------------- Slots --------------------
    @Override
    public List<LocalDateTime> getAvailableSlots(UUID dentistId, LocalDate date) {

        // 1️⃣ Validation
        validationService.validateDentistId(dentistId);
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        // 2️⃣ Récupérer les horaires de travail du dentiste
        // Exemple simple : 09:00 - 17:00
        LocalDateTime workStart = date.atTime(9, 0);
        LocalDateTime workEnd = date.atTime(17, 0);

        // Durée standard du créneau (30 min ici, peut être paramétrable)
        int slotMinutes = 30;

        // 3️⃣ Récupérer tous les rendez-vous existants du dentiste pour ce jour
        List<Appointment> appointments = appointmentRepository
                .findByDentistIdAndAppointmentDateTimeBetween(
                        dentistId,
                        workStart,
                        workEnd
                );

        // 4️⃣ Générer tous les créneaux possibles
        List<LocalDateTime> allSlots = generateTimeSlots(workStart, workEnd, slotMinutes);

        // 5️⃣ Filtrer les créneaux déjà occupés

        return allSlots.stream()
                .filter(slot -> appointments.stream()
                        .noneMatch(a -> isOverlapping(slot, slot.plusMinutes(slotMinutes),
                                a.getAppointmentDateTime(), a.getEndDateTime()))
                )
                .toList();
    }

// -------------------- PRIVATE UTILS --------------------

    /**
     * Génère tous les créneaux entre workStart et workEnd
     */
    private List<LocalDateTime> generateTimeSlots(LocalDateTime start, LocalDateTime end, int slotMinutes) {
        List<LocalDateTime> slots = new java.util.ArrayList<>();
        LocalDateTime current = start;
        while (!current.plusMinutes(slotMinutes).isAfter(end)) {
            slots.add(current);
            current = current.plusMinutes(slotMinutes);
        }
        return slots;
    }

    /**
     * Vérifie le chevauchement entre deux périodes
     */
    private boolean isOverlapping(LocalDateTime slotStart, LocalDateTime slotEnd,
                                  LocalDateTime appointmentStart, LocalDateTime appointmentEnd) {
        return slotStart.isBefore(appointmentEnd) && slotEnd.isAfter(appointmentStart);
    }

}
