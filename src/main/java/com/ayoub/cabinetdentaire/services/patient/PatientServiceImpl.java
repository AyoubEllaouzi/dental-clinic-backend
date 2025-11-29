package com.ayoub.cabinetdentaire.services.patient;

import com.ayoub.cabinetdentaire.dtos.patient.PatientRequest;
import com.ayoub.cabinetdentaire.dtos.patient.PatientResponse;
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
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setAddress(request.getAddress());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setSocialSecurityNumber(request.getSocialSecurityNumber());
        entity.setGender(request.getGender());
        entity.setEmergencyContact(request.getEmergencyContact());
        entity.setEmergencyPhone(request.getEmergencyPhone());
    }

    @Override
    protected Specification<Patient> searchSpecification(String keyword) {
        return (root, query, cb) ->
                cb.or(
                        cb.like(cb.lower(root.get("firstName")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("lastName")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("email")), "%" + keyword.toLowerCase() + "%")
                );
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
    public List<PatientResponse> getPatientsByRoleName(String roleName) {
        return patientRepository.findAll().stream()
                .filter(p -> p.getRoles().stream()
                        .anyMatch(r -> r.getName().equalsIgnoreCase(roleName)))
                .map(this::toResponse)
                .toList();
    }

    @Override
    public Page<PatientResponse> search(String keyword, Pageable pageable) {
        return patientRepository.findAll(searchSpecification(keyword), pageable)
                .map(this::toResponse);
    }
}
