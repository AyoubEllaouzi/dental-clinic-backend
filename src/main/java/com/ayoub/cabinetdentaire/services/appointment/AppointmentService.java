package com.ayoub.cabinetdentaire.services.appointment;

import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentRequest;
import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentResponse;
import com.ayoub.cabinetdentaire.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse createAppointment(AppointmentRequest request);

    AppointmentResponse getAppointmentById(UUID id);

    List<AppointmentResponse> getAllAppointments();

    List<AppointmentResponse> getAppointmentsByPatient(UUID patientId);

    List<AppointmentResponse> getAppointmentsByDentist(UUID dentistId);

    List<AppointmentResponse> getAppointmentsByStatus(AppointmentStatus status);

    List<AppointmentResponse> getAppointmentsByDate(LocalDate date);

    List<LocalDateTime> getAvailableSlots(UUID dentistId, LocalDate date);

    AppointmentResponse updateAppointment(UUID id, AppointmentRequest request);

    AppointmentResponse updateAppointmentStatus(UUID id, AppointmentStatus status);

    void cancelAppointment(UUID id, String reason);

    void deleteAppointment(UUID id);

    boolean isDentistAvailable(UUID dentistId, LocalDateTime startTime, LocalDateTime endTime);

    List<AppointmentResponse> getUpcomingAppointments();

    List<AppointmentResponse> getAppointmentsBetweenDates(LocalDateTime start, LocalDateTime end);

    long countAppointmentsByStatus(AppointmentStatus status);
}
