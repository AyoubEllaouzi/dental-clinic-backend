package com.ayoub.cabinetdentaire.mappers;

import com.ayoub.cabinetdentaire.dtos.patient.PatientRequest;
import com.ayoub.cabinetdentaire.dtos.patient.PatientResponse;
import com.ayoub.cabinetdentaire.entities.Patient;
import org.springframework.stereotype.Component;

@Component
public class PatientMapper {
    public Patient toEntity(PatientRequest request) {
        if (request == null){
            return null;
        }

        return Patient.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .socialSecurityNumber(request.getSocialSecurityNumber())
                .gender(request.getGender())
                .emergencyContact(request.getEmergencyContact())
                .emergencyPhone(request.getEmergencyPhone())
                .enabled(true)
                .build();

    }

    public PatientResponse toResponse(Patient patient) {
        if (patient == null){
            return null;
        }

        return PatientResponse.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth())
                .socialSecurityNumber(patient.getSocialSecurityNumber())
                .gender(patient.getGender())
                .emergencyContact(patient.getEmergencyContact())
                .emergencyPhone(patient.getEmergencyPhone())
                .build();

    }
}
