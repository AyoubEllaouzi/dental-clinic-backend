package com.ayoub.cabinetdentaire.mappers;

import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentRequest;
import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentResponse;
import com.ayoub.cabinetdentaire.entities.Treatment;
import org.springframework.stereotype.Component;

@Component
public class TreatmentMapper {

    public void toEntity(TreatmentRequest request){
        Treatment.builder()
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .type(request.getType())
            .duration(request.getDuration())
            .build();
    }

    public TreatmentResponse toResponse(Treatment treatment){
        return TreatmentResponse.builder()
                .id(treatment.getId())
                .name(treatment.getName())
                .description(treatment.getDescription())
                .price(treatment.getPrice())
                .type(treatment.getType())
                .duration(treatment.getDuration())
                .consultationId(treatment.getConsultation().getId())
                .build();
    }
}
