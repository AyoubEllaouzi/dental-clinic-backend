package com.ayoub.cabinetdentaire.mappers;

import com.ayoub.cabinetdentaire.dtos.dentist.DentistRequest;
import com.ayoub.cabinetdentaire.dtos.dentist.DentistResponse;
import com.ayoub.cabinetdentaire.entities.Dentist;
import org.springframework.stereotype.Component;

@Component
public class DentistMapper {

    public Dentist toEntity(DentistRequest request) {
        if (request == null) {
            return null;
        }

        return Dentist.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .licenseNumber(request.getLicenseNumber())
                .specialization(request.getSpecialization())
                .hourlyRate(request.getHourlyRate())
                .officeHours(request.getOfficeHours())
                .enabled(true)
                .build();
    }

    public DentistResponse toResponse(Dentist dentist) {
        if (dentist == null) {
            return null;
        }

        return DentistResponse.builder()
                .id(dentist.getId())
                .firstName(dentist.getFirstName())
                .lastName(dentist.getLastName())
                .email(dentist.getEmail())
                .phone(dentist.getPhone())
                .address(dentist.getAddress())
                .licenseNumber(dentist.getLicenseNumber())
                .specialization(dentist.getSpecialization())
                .hourlyRate(dentist.getHourlyRate())
                .officeHours(dentist.getOfficeHours())
                .build();

    }
}
