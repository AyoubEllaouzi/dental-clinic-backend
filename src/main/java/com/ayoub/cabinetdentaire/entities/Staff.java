package com.ayoub.cabinetdentaire.entities;

import com.ayoub.cabinetdentaire.enums.StaffType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "staff")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id")
public class Staff extends User{
    @Enumerated(EnumType.STRING)
    private StaffType staffType;

    private String employeeId;

    private String department;
}
