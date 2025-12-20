package com.ayoub.cabinetdentaire.repoositories;

import com.ayoub.cabinetdentaire.entities.Treatment;
import com.ayoub.cabinetdentaire.enums.TreatmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, UUID> {
    List<Treatment> findByType(TreatmentType type);

    List<Treatment> findByConsultation_Id(UUID consultationId);

    List<Treatment> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    long countByType(TreatmentType type);

    List<Treatment> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String keyword1);
}
