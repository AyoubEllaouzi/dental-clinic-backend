package com.ayoub.cabinetdentaire.repoositories;

import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentResponse;
import com.ayoub.cabinetdentaire.entities.Appointment;
import com.ayoub.cabinetdentaire.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientId(UUID patientId);

    List<Appointment> findByDentistId(UUID dentistId);

    List<Appointment> findByStatus(AppointmentStatus status);

    @Query("SELECT a FROM Appointment a WHERE DATE(a.appointmentDateTime) = :date")
    List<Appointment> findByAppointmentDate(@Param("date") LocalDate date);

    boolean existsByDentistIdAndAppointmentDateTimeLessThanAndEndDateTimeGreaterThan(UUID dentistId, LocalDateTime endTime, LocalDateTime startTime);

    List<Appointment> findByAppointmentDateTimeAfterOrderByAppointmentDateTimeAsc(
            LocalDateTime now
    );

    List<Appointment> findByAppointmentDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    long countByStatus(AppointmentStatus status);

    List<Appointment> findByDentistIdAndAppointmentDateTimeBetween(UUID dentistId, LocalDateTime workStart, LocalDateTime workEnd);
}
