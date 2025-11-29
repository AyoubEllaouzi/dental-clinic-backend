package com.ayoub.cabinetdentaire.services.consultation;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;
import com.ayoub.cabinetdentaire.entities.Consultation;
import com.ayoub.cabinetdentaire.mappers.ConsultationMapper;
import com.ayoub.cabinetdentaire.repoositories.ConsultationRepository;
import com.ayoub.cabinetdentaire.services.EntityValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final EntityValidationService validationService;

    @Override
    public ConsultationResponse createConsultation(ConsultationRequest request) {
        Consultation consultation = new Consultation();

        consultation.setPatient(validationService.validatePatientId(request.getPatientId()));
        consultation.setDentist(validationService.validateDentistId(request.getDentistId()));
        consultation.setAppointment(validationService.validateAppointmentId(request.getAppointmentId()));
        consultation.setTreatments(validationService.validateTreatmentIds(request.getTreatmentsId()));

        consultationMapper.toEntity(request);
        consultationRepository.save(consultation);

        return consultationMapper.toResponse(consultation);
    }

    @Override
    public ConsultationResponse createConsultationFromAppointment(UUID appointmentId) {
        return null;
    }

    @Override
    public ConsultationResponse getConsultationById(UUID id) {
        return consultationMapper.toResponse(validationService.validateConsultationId(id));
    }

    @Override
    public ConsultationResponse getAllConsultations() {
        return null;
    }

    @Override
    public ConsultationResponse getConsultationsByPatient(UUID patientId) {
        return null;
    }

    @Override
    public ConsultationResponse getConsultationsByDentist(UUID dentistId) {
        return null;
    }

    @Override
    public ConsultationResponse getConsultationsByDate(LocalDate date) {
        return null;
    }

    @Override
    public ConsultationResponse updateConsultation(UUID id, ConsultationRequest request) {
        return null;
    }

    @Override
    public void deleteConsultation(UUID id) {

    }

    @Override
    public ConsultationResponse addTreatmentToConsultation(UUID consultationId, UUID treatmentId) {
        return null;
    }

    @Override
    public ConsultationResponse removeTreatmentFromConsultation(UUID consultationId, UUID treatmentId) {
        return null;
    }

    @Override
    public ConsultationResponse generateInvoice(UUID consultationId) {
        return null;
    }

    @Override
    public ConsultationResponse getConsultationsWithTreatments() {
        return null;
    }

    @Override
    public ConsultationResponse getConsultationWithDetails(UUID id) {
        return null;
    }
}
