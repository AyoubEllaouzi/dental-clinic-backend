package com.ayoub.cabinetdentaire.services.staff;

import com.ayoub.cabinetdentaire.dtos.staff.StaffResponse;
import com.ayoub.cabinetdentaire.enums.StaffType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StaffService {
    StaffResponse getStaffProfile(UUID userId);
    // StaffResponse updateStaffInfo(UUID userId, StaffUpdateDto updateDto);
    Page<StaffResponse> getAllStaff(Pageable pageable);
    Page<StaffResponse> getStaffByType(StaffType staffType, Pageable pageable);
    long getTotalStaff();
}
