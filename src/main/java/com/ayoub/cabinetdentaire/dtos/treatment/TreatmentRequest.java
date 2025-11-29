package com.ayoub.cabinetdentaire.dtos.treatment;

import com.ayoub.cabinetdentaire.enums.TreatmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TreatmentRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private TreatmentType type;
    private Integer duration;
    private UUID consultationId;
}
