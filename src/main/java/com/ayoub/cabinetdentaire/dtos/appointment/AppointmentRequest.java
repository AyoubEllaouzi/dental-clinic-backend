package com.ayoub.cabinetdentaire.dtos.appointment;

import com.ayoub.cabinetdentaire.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {
    private UUID patientId;
    private UUID dentistId;
    private LocalDateTime appointmentDateTime;
    private LocalDateTime endDateTime;
    private AppointmentStatus status;
    private String notes;
    private UUID consultationId;
}
