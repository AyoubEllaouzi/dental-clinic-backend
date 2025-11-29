package com.ayoub.cabinetdentaire.dtos.appointment;

import com.ayoub.cabinetdentaire.enums.AppointmentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AppointmentResponse {
    private UUID appointmentId;
    private UUID patientId;
    private UUID dentistId;
    private LocalDateTime appointmentDateTime;
    private LocalDateTime endDateTime;
    private AppointmentStatus status;
    private String notes;
    private UUID consultationId;
}
