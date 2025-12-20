package com.ayoub.cabinetdentaire.services.consultation;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;
import com.ayoub.cabinetdentaire.entities.Appointment;
import com.ayoub.cabinetdentaire.entities.Consultation;
import com.ayoub.cabinetdentaire.entities.Treatment;
import com.ayoub.cabinetdentaire.mappers.ConsultationMapper;
import com.ayoub.cabinetdentaire.repoositories.ConsultationRepository;
import com.ayoub.cabinetdentaire.services.EntityValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConsultationServiceImpl implements ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;
    private final EntityValidationService validationService;

    @Override
    public ConsultationResponse createConsultation(ConsultationRequest request) {
        Consultation consultation = consultationMapper.toEntity(request);

        consultation.setPatient(validationService.validatePatientId(request.getPatientId()));
        consultation.setDentist(validationService.validateDentistId(request.getDentistId()));
        consultation.setAppointment(validationService.validateAppointmentId(request.getAppointmentId()));
        consultation.setTreatments(validationService.validateTreatmentIds(request.getTreatmentsId()));

        consultationRepository.save(consultation);
        log.info("Consultation created: {}", consultation);
        return consultationMapper.toResponse(consultation);
    }

    @Override
    public ConsultationResponse createConsultationFromAppointment(UUID appointmentId) {
        Appointment appointment = validationService.validateAppointmentId(appointmentId);

        Consultation consultation = Consultation.builder()
                .appointment(appointment)
                .patient(appointment.getPatient())
                .dentist(appointment.getDentist())
                .consultationDate(appointment.getAppointmentDateTime())
                .build();

        consultationRepository.save(consultation);

        return consultationMapper.toResponse(consultation);
    }


    @Override
    public ConsultationResponse getConsultationById(UUID id) {
        return consultationMapper.toResponse(validationService.validateConsultationId(id));
    }

    @Override
    public List<ConsultationResponse> getAllConsultations() {
        return consultationRepository.findAll()
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Override
    public List<ConsultationResponse> getConsultationsByPatient(UUID patientId) {
        return consultationRepository.findByPatient_id(patientId)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Override
    public List<ConsultationResponse> getConsultationsByDentist(UUID dentistId) {
        return consultationRepository.findByDentist_id(dentistId)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Override
    public List<ConsultationResponse> getConsultationsByDate(LocalDateTime date) {
        return consultationRepository.findByConsultationDate(date)
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Override
    public ConsultationResponse updateConsultation(UUID id, ConsultationRequest request) {
        Consultation consultation = validationService.validateConsultationId(id);

        consultationMapper.updateEntity(consultation, request);

        consultationRepository.save(consultation);
        log.info("Consultation updated: {}", consultation);
        return consultationMapper.toResponse(consultation);
    }


    @Override
    public void deleteConsultation(UUID id) {
        validationService.validateConsultationId(id);
        consultationRepository.deleteById(id);
        log.info("Consultation deleted: {}", consultationRepository.findById(id));
    }

    @Override
    public ConsultationResponse addTreatmentToConsultation(UUID consultationId, UUID treatmentId) {
        Consultation consultation = validationService.validateConsultationIdWithDetails(consultationId);

        Treatment treatment = validationService.validateTreatmentId(treatmentId);

        // Add treatment to consultation
        consultation.getTreatments().add(treatment);
        treatment.setConsultation(consultation);

        consultationRepository.save(consultation);
        log.info("Consultation added: {}", consultation);
        return consultationMapper.toResponse(consultation);
    }


    @Override
    public ConsultationResponse removeTreatmentFromConsultation(UUID consultationId, UUID treatmentId) {
        Consultation consultation = validationService.validateConsultationIdWithDetails(consultationId);

        Treatment treatment = validationService.validateTreatmentId(treatmentId);

        consultation.getTreatments().remove(treatment);
        treatment.setConsultation(null);

        consultationRepository.save(consultation);
        log.info("Consultation deleted from consultation: {}", consultation);

        return consultationMapper.toResponse(consultation);
    }

    @Override
    public List<ConsultationResponse> getConsultationsWithTreatments() {
        return consultationRepository.findAllWithTreatments()
                .stream()
                .map(consultationMapper::toResponse)
                .toList();
    }

    @Override
    public ConsultationResponse getConsultationWithDetails(UUID consultationId) {
        return consultationMapper.toResponse(validationService.validateConsultationIdWithDetails(consultationId));
    }
}
