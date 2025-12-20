package com.ayoub.cabinetdentaire.services.consultation;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    ConsultationResponse createConsultation(ConsultationRequest request);

    ConsultationResponse createConsultationFromAppointment(UUID appointmentId);

    ConsultationResponse getConsultationById(UUID id);

    List<ConsultationResponse> getAllConsultations();

    List<ConsultationResponse> getConsultationsByPatient(UUID patientId);

    List<ConsultationResponse> getConsultationsByDentist(UUID dentistId);

    List<ConsultationResponse> getConsultationsByDate(LocalDateTime date);

    ConsultationResponse updateConsultation(UUID id, ConsultationRequest request);

    void deleteConsultation(UUID id);

    ConsultationResponse addTreatmentToConsultation(UUID consultationId, UUID treatmentId);

    ConsultationResponse removeTreatmentFromConsultation(UUID consultationId, UUID treatmentId);

    // ConsultationResponse generateInvoice(UUID consultationId);

    List<ConsultationResponse> getConsultationsWithTreatments();

    ConsultationResponse getConsultationWithDetails(UUID id);
}
