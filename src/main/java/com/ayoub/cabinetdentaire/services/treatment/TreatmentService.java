package com.ayoub.cabinetdentaire.services.treatment;

import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentRequest;
import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentResponse;
import com.ayoub.cabinetdentaire.enums.TreatmentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TreatmentService {
    TreatmentResponse createTreatment(TreatmentRequest request);

    TreatmentResponse getTreatmentById(UUID id);

    List<TreatmentResponse> getAllTreatments();

    List<TreatmentResponse> getTreatmentsByType(TreatmentType type);

    List<TreatmentResponse> getTreatmentsByConsultation(UUID consultationId);

    TreatmentResponse updateTreatment(UUID id, TreatmentRequest request);

    void deleteTreatment(UUID id);

    List<TreatmentResponse> searchTreatments(String keyword);

    List<TreatmentResponse> getTreatmentsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    TreatmentResponse updateTreatmentPrice(UUID id, BigDecimal newPrice);

    long countTreatmentsByType(TreatmentType type);
}
