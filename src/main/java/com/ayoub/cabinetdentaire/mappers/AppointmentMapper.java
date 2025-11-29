package com.ayoub.cabinetdentaire.mappers;

import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentRequest;
import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentResponse;
import com.ayoub.cabinetdentaire.entities.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(AppointmentRequest request){
        if (request == null) {
            return null;
        }

        return Appointment.builder()
                .appointmentDateTime(request.getAppointmentDateTime())
                .endDateTime(request.getEndDateTime())
                .status(request.getStatus())
                .notes(request.getNotes())
                .build();
    }

    public void updateEntity(Appointment appointment, AppointmentRequest request) {
        appointment.setAppointmentDateTime(request.getAppointmentDateTime());
        appointment.setEndDateTime(request.getEndDateTime());
        appointment.setStatus(request.getStatus());
        appointment.setNotes(request.getNotes());
    }

    public AppointmentResponse toResponse(Appointment appointment){
        if (appointment == null) {
            return null;
        }

        return AppointmentResponse.builder()
                .appointmentId(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .dentistId(appointment.getDentist().getId())
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .endDateTime(appointment.getEndDateTime())
                .status(appointment.getStatus())
                .notes(appointment.getNotes())
                .consultationId(appointment.getConsultation().getId())
                .build();
    }
}
