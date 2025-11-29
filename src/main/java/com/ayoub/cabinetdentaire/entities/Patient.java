package com.ayoub.cabinetdentaire.entities;

import com.ayoub.cabinetdentaire.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Patient extends User {
    private LocalDate dateOfBirth;
    private String socialSecurityNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String emergencyContact;
    private String emergencyPhone;
}
