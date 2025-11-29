package com.ayoub.cabinetdentaire.mappers;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;
import com.ayoub.cabinetdentaire.entities.Consultation;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public void toEntity(ConsultationRequest request){
        Consultation.builder()
            .consultationDate(request.getConsultationDate())
            .medicalNotes(request.getMedicalNotes())
            .diagnosis(request.getDiagnosis())
            .treatmentPlan(request.getTreatmentPlan())
            .build();
    }

    public ConsultationResponse toResponse(Consultation consultation){
        return ConsultationResponse.builder()
                .appointmentId(consultation.getId())
                .appointmentId(consultation.getAppointment().getId())
                .patientId(consultation.getPatient().getId())
                .dentistId(consultation.getDentist().getId())
                .consultationDate(consultation.getConsultationDate())
                .medicalNotes(consultation.getMedicalNotes())
                .diagnosis(consultation.getDiagnosis())
                .treatmentPlan(consultation.getTreatmentPlan())
                //.treatmentsId((List<UUID>) consultation.getTreatments().getFirst())
                .build();
    }
}
