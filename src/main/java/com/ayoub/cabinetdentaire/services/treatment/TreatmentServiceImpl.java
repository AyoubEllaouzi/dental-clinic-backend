package com.ayoub.cabinetdentaire.services.treatment;

import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentRequest;
import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentResponse;
import com.ayoub.cabinetdentaire.entities.Consultation;
import com.ayoub.cabinetdentaire.entities.Treatment;
import com.ayoub.cabinetdentaire.enums.TreatmentType;
import com.ayoub.cabinetdentaire.mappers.TreatmentMapper;
import com.ayoub.cabinetdentaire.repoositories.TreatmentRepository;
import com.ayoub.cabinetdentaire.services.EntityValidationService;
import com.ayoub.cabinetdentaire.services.consultation.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TreatmentServiceImpl implements TreatmentService {
    private final ConsultationService consultationService;
    private final TreatmentMapper treatmentMapper;
    private final EntityValidationService validationService;

    @Override
    public TreatmentResponse createTreatment(TreatmentRequest request) {
        Treatment treatment = new Treatment();
        Consultation consultation = validationService.validateConsultationId(request.getConsultationId());

        treatmentMapper.toEntity(request);
        treatment.setConsultation(consultation);

        return treatmentMapper.toResponse(treatment);
    }

    @Override
    public TreatmentResponse getTreatmentById(UUID id) {
        return null;
    }

    @Override
    public TreatmentResponse getAllTreatments() {
        return null;
    }

    @Override
    public TreatmentResponse getTreatmentsByType(TreatmentType type) {
        return null;
    }

    @Override
    public List<TreatmentResponse> getTreatmentsByConsultation(UUID consultationId) {
        return List.of();
    }

    @Override
    public TreatmentResponse updateTreatment(UUID id, TreatmentRequest request) {
        return null;
    }

    @Override
    public void deleteTreatment(UUID id) {}

    @Override
    public TreatmentResponse searchTreatments(String keyword) {
        return null;
    }

    @Override
    public List<TreatmentResponse> getTreatmentsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return List.of();
    }

    @Override
    public TreatmentResponse updateTreatmentPrice(UUID id, BigDecimal newPrice) {
        return null;
    }

    @Override
    public long countTreatmentsByType(TreatmentType type) {
        return 0;
    }
}
