package com.ayoub.cabinetdentaire.repoositories;

import com.ayoub.cabinetdentaire.entities.Dentist;
import com.ayoub.cabinetdentaire.enums.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DentistRepository extends BaseUserRepository<Dentist, UUID>, JpaSpecificationExecutor<Dentist> {
    Optional<Dentist> findByLicenseNumber(String licenseNumber);
    Page<Dentist> findBySpecialization(Specialization specialization, Pageable pageable);
}
