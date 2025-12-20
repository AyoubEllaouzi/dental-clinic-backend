package com.ayoub.cabinetdentaire.mappers;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;
import com.ayoub.cabinetdentaire.entities.Consultation;
import com.ayoub.cabinetdentaire.entities.Treatment;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public Consultation toEntity(ConsultationRequest request) {
        return Consultation.builder()
                .consultationDate(request.getConsultationDate())
                .medicalNotes(request.getMedicalNotes())
                .diagnosis(request.getDiagnosis())
                .treatmentPlan(request.getTreatmentPlan())
                .build();
    }

    public ConsultationResponse toResponse(Consultation consultation) {
        return ConsultationResponse.builder()
                .consultationId(consultation.getId())
                .appointmentId(consultation.getAppointment() != null ? consultation.getAppointment().getId() : null)
                .patientId(consultation.getPatient().getId())
                .dentistId(consultation.getDentist().getId())
                .consultationDate(consultation.getConsultationDate())
                .medicalNotes(consultation.getMedicalNotes())
                .diagnosis(consultation.getDiagnosis())
                .treatmentPlan(consultation.getTreatmentPlan())
                .treatmentsId(
                        consultation.getTreatments()
                                .stream()
                                .map(Treatment::getId)
                                .toList()
                )
                .build();
    }

    public void updateEntity(Consultation consultation, ConsultationRequest request) {
        consultation.setConsultationDate(request.getConsultationDate());
        consultation.setMedicalNotes(request.getMedicalNotes());
        consultation.setDiagnosis(request.getDiagnosis());
        consultation.setTreatmentPlan(request.getTreatmentPlan());
    }
}
