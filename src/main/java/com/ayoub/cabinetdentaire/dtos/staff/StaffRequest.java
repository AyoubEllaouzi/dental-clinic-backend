package com.ayoub.cabinetdentaire.dtos.staff;

import com.ayoub.cabinetdentaire.enums.StaffType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private StaffType staffType;
    private String employeeId;
    private String department;
}
