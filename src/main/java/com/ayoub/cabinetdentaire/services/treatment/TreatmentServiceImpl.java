package com.ayoub.cabinetdentaire.services.treatment;

import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentRequest;
import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentResponse;
import com.ayoub.cabinetdentaire.entities.Treatment;
import com.ayoub.cabinetdentaire.enums.TreatmentType;
import com.ayoub.cabinetdentaire.mappers.TreatmentMapper;
import com.ayoub.cabinetdentaire.repoositories.TreatmentRepository;
import com.ayoub.cabinetdentaire.services.EntityValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TreatmentServiceImpl implements TreatmentService {
    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;
    private final EntityValidationService validationService;

    @Override
    public TreatmentResponse createTreatment(TreatmentRequest request) {
        Treatment treatment = treatmentMapper.toEntity(request);

        treatment.setConsultation(validationService.validateConsultationId(request.getConsultationId()));

        treatmentRepository.save(treatment);
        log.info("Created treatment {}", treatment);
        return treatmentMapper.toResponse(treatment);
    }

    @Override
    public TreatmentResponse getTreatmentById(UUID id) {
        return treatmentMapper.toResponse(validationService.validateTreatmentId(id));
    }

    @Override
    public List<TreatmentResponse> getAllTreatments() {
        return treatmentRepository.findAll()
                .stream()
                .map(treatmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<TreatmentResponse> getTreatmentsByType(TreatmentType type) {
        return treatmentRepository.findByType(type)
                .stream()
                .map(treatmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<TreatmentResponse> getTreatmentsByConsultation(UUID consultationId) {
        return treatmentRepository.findByConsultation_Id(consultationId)
                .stream()
                .map(treatmentMapper::toResponse)
                .toList();
    }

    @Override
    public TreatmentResponse updateTreatment(UUID id, TreatmentRequest request) {
        Treatment treatment = validationService.validateTreatmentId(id);

        treatmentMapper.updateEntity(treatment, request);
        treatment.setConsultation(validationService.validateConsultationId(request.getConsultationId()));
        treatmentRepository.save(treatment);
        log.info("Updated treatment {}", treatment);

        return treatmentMapper.toResponse(treatment);
    }

    @Override
    public void deleteTreatment(UUID id) {
        validationService.validateTreatmentId(id);
        treatmentRepository.deleteById(id);
        log.info("Deleted treatment {}", id);
    }

    @Override
    public List<TreatmentResponse> searchTreatments(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        return treatmentRepository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(treatmentMapper::toResponse)
                .toList();
    }

    @Override
    public List<TreatmentResponse> getTreatmentsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return treatmentRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(treatmentMapper::toResponse)
                .toList();
    }

    @Override
    public TreatmentResponse updateTreatmentPrice(UUID id, BigDecimal newPrice) {

        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        Treatment treatment = treatmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Treatment not found with id: " + id));

        treatment.setPrice(newPrice);

        Treatment updatedTreatment = treatmentRepository.save(treatment);

        return treatmentMapper.toResponse(updatedTreatment);
    }

    @Override
    public long countTreatmentsByType(TreatmentType type) {
        return treatmentRepository.countByType(type);
    }
}
