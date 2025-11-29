package com.ayoub.cabinetdentaire.services.patient;

import com.ayoub.cabinetdentaire.dtos.PatientRequest;
import com.ayoub.cabinetdentaire.dtos.PatientResponse;
import com.ayoub.cabinetdentaire.services.base.BaseUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PatientService
        extends BaseUserService<PatientRequest, PatientResponse, UUID> {
    Page<PatientResponse> getPatientsByDentist(UUID dentistId, Pageable pageable);
    PatientResponse getPatientWithMedicalRecord(UUID userId);
    List<PatientRequest> getPatientsByRole(String role);
}
