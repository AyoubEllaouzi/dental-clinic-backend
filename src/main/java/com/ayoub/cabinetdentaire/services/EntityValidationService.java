package com.ayoub.cabinetdentaire.services;

import com.ayoub.cabinetdentaire.entities.*;
import com.ayoub.cabinetdentaire.repoositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntityValidationService {
    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;
    private final DentistRepository dentistRepository;
    private final TreatmentRepository treatmentRepository;

    public Appointment validateAppointmentId(UUID id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));
    }

    public Consultation validateConsultationId(UUID id) {
        return consultationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found with id: " + id));
    }

    public Patient validatePatientId(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
    }


    public Dentist validateDentistId(UUID id) {
        return dentistRepository.findById(id).orElseThrow(() -> new RuntimeException("Dentist not found with id: " + id));
    }

    public Treatment validateTreatmentId(UUID id) {
        return treatmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Treatment not found with id: " + id));
    }

    public List<Treatment> validateTreatmentIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<UUID> uniqueIds = ids.stream()
                .distinct()
                .toList();

        List<Treatment> treatments = treatmentRepository.findAllById(uniqueIds);

        if (treatments.size() != uniqueIds.size()) {
            Set<UUID> foundIds = treatments.stream()
                    .map(Treatment::getId)
                    .collect(Collectors.toSet());

            List<UUID> missingIds = uniqueIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new EntityNotFoundException(
                    String.format("Some treatments not found. Missing IDs: %s", missingIds)
            );
        }

        return treatments;
    }

    public Consultation validateConsultationIdWithDetails(UUID consultationId) {
        return consultationRepository.findByIdWithDetails(consultationId)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found with id: " + consultationId));
    }
}
