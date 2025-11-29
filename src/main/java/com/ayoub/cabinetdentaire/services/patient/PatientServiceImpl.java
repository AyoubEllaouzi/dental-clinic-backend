package com.ayoub.cabinetdentaire.services.patient;

import com.ayoub.cabinetdentaire.dtos.PatientRequest;
import com.ayoub.cabinetdentaire.dtos.PatientResponse;
import com.ayoub.cabinetdentaire.entities.Patient;
import com.ayoub.cabinetdentaire.mappers.PatientMapper;
import com.ayoub.cabinetdentaire.repoositories.BaseUserRepository;
import com.ayoub.cabinetdentaire.repoositories.PatientRepository;
import com.ayoub.cabinetdentaire.services.base.BaseUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl
        extends BaseUserServiceImpl<Patient, PatientRequest, PatientResponse, UUID>
        implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    protected BaseUserRepository<Patient, UUID> getRepository() {
        return patientRepository;
    }

    @Override
    protected Patient toEntity(PatientRequest request) {
        return patientMapper.toEntity(request);
    }

    @Override
    protected PatientResponse toResponse(Patient entity) {
        return patientMapper.toResponse(entity);
    }

    @Override
    protected void updateEntity(Patient entity, PatientRequest request) {

    }

    @Override
    protected Specification<Patient> searchSpecification(String keyword) {
        return null;
    }

    @Override
    public Page<PatientResponse> getPatientsByDentist(UUID dentistId, Pageable pageable) {
        return null;
    }

    @Override
    public PatientResponse getPatientWithMedicalRecord(UUID userId) {
        return null;
    }

    @Override
    public List<PatientRequest> getPatientsByRole(String role) {
        return List.of();
    }

    @Override
    public Page<PatientResponse> search(String keyword, Pageable pageable) {
        return null;
    }
}
