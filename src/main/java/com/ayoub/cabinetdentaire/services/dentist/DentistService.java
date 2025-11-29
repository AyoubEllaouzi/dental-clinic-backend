package com.ayoub.cabinetdentaire.services.dentist;

import com.ayoub.cabinetdentaire.dtos.dentist.DentistRequest;
import com.ayoub.cabinetdentaire.dtos.dentist.DentistResponse;
import com.ayoub.cabinetdentaire.entities.Dentist;
import com.ayoub.cabinetdentaire.enums.Specialization;
import com.ayoub.cabinetdentaire.services.base.BaseUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface DentistService extends BaseUserService<DentistRequest, DentistResponse, UUID> {
    Page<DentistResponse> getDentistsBySpecialization(Specialization specialization, Pageable pageable);
    DentistResponse getDentistByLicenseNumber(String licenseNumber);
    DentistResponse updateDentistSchedule(UUID userId, String schedule);
    List<DentistResponse> getDentistsByRoleName(String roleName);
}
