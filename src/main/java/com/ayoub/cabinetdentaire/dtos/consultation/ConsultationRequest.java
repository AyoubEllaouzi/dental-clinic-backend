package com.ayoub.cabinetdentaire.dtos.consultation;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ConsultationRequest {
    private UUID appointmentId;
    private UUID patientId;
    private UUID dentistId;
    private LocalDateTime consultationDate;
    private String medicalNotes;
    private String diagnosis;
    private String treatmentPlan;
    private List<UUID> treatmentsId = new ArrayList<>();
}
