package com.ayoub.cabinetdentaire.entities;

import com.ayoub.cabinetdentaire.enums.Specialization;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "dentists")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Dentist extends User{
    private String licenseNumber;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Column(precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    private String officeHours;
}
