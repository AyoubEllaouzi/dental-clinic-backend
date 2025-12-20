package com.ayoub.cabinetdentaire.repoositories;

import com.ayoub.cabinetdentaire.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {
    List<Consultation> findByPatient_id(UUID patientId);

    List<Consultation> findByDentist_id(UUID dentistId);

    List<Consultation>  findByConsultationDate(LocalDateTime consultationDate);

    @Query("SELECT DISTINCT c FROM Consultation c LEFT JOIN FETCH c.treatments")
    List<Consultation> findAllWithTreatments();


    @Query("SELECT c FROM Consultation c " +
            "LEFT JOIN FETCH c.treatments " +
            "LEFT JOIN FETCH c.patient " +
            "LEFT JOIN FETCH c.dentist " +
            "WHERE c.id = :id")
    Optional<Consultation> findByIdWithDetails(@Param("id") UUID id);

}
