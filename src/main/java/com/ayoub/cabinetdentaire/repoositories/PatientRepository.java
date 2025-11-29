package com.ayoub.cabinetdentaire.repoositories;

import com.ayoub.cabinetdentaire.entities.Patient;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends BaseUserRepository<Patient, UUID>, JpaSpecificationExecutor<Patient> {
}
