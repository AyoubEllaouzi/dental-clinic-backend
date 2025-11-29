package com.ayoub.cabinetdentaire.dtos.dentist;

import com.ayoub.cabinetdentaire.enums.Specialization;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DentistRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String licenseNumber;
    private Specialization specialization;
    private BigDecimal hourlyRate;
    private String officeHours;
}
