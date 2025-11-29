package com.ayoub.cabinetdentaire.dtos.patient;

import com.ayoub.cabinetdentaire.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String socialSecurityNumber;
    private Gender gender;
    private String emergencyContact;
    private String emergencyPhone;
}
