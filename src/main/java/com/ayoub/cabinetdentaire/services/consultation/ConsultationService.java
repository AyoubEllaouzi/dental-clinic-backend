package com.ayoub.cabinetdentaire.services.consultation;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;

import java.time.LocalDate;
import java.util.UUID;

public interface ConsultationService {
    ConsultationResponse createConsultation(ConsultationRequest request);

    ConsultationResponse createConsultationFromAppointment(UUID appointmentId);

    ConsultationResponse getConsultationById(UUID id);

    ConsultationResponse getAllConsultations();

    ConsultationResponse getConsultationsByPatient(UUID patientId);

    ConsultationResponse getConsultationsByDentist(UUID dentistId);

    ConsultationResponse getConsultationsByDate(LocalDate date);

    ConsultationResponse updateConsultation(UUID id, ConsultationRequest request);

    void deleteConsultation(UUID id);

    ConsultationResponse addTreatmentToConsultation(UUID consultationId, UUID treatmentId);

    ConsultationResponse removeTreatmentFromConsultation(UUID consultationId, UUID treatmentId);

    ConsultationResponse generateInvoice(UUID consultationId);

    ConsultationResponse getConsultationsWithTreatments();

    ConsultationResponse getConsultationWithDetails(UUID id);
}
