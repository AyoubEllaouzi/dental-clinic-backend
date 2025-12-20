package com.ayoub.cabinetdentaire.controllers;

import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationRequest;
import com.ayoub.cabinetdentaire.dtos.consultation.ConsultationResponse;
import com.ayoub.cabinetdentaire.services.consultation.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consultations")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultationController {

    private final ConsultationService consultationService;

    // Create a new consultation
    @PostMapping
    public ResponseEntity<ConsultationResponse> createConsultation(@RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.createConsultation(request));
    }

    // Create a consultation from an appointment
    @PostMapping("/from-appointment/{appointmentId}")
    public ResponseEntity<ConsultationResponse> createConsultationFromAppointment(@PathVariable UUID appointmentId) {
        return ResponseEntity.ok(consultationService.createConsultationFromAppointment(appointmentId));
    }

    // Get consultation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationById(@PathVariable UUID id) {
        return ResponseEntity.ok(consultationService.getConsultationById(id));
    }

    // Get all consultations
    @GetMapping("/all")
    public ResponseEntity<List<ConsultationResponse>> getAllConsultations() {
        return ResponseEntity.ok(consultationService.getAllConsultations());
    }

    // Get consultations by patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ConsultationResponse>> getConsultationsByPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(consultationService.getConsultationsByPatient(patientId));
    }

    // Get consultations by dentist
    @GetMapping("/dentist/{dentistId}")
    public ResponseEntity<List<ConsultationResponse>> getConsultationsByDentist(@PathVariable UUID dentistId) {
        return ResponseEntity.ok(consultationService.getConsultationsByDentist(dentistId));
    }

    // Get consultations by date
    @GetMapping("/date")
    public ResponseEntity<List<ConsultationResponse>> getConsultationsByDate(@RequestParam String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date); // ensure correct format
        return ResponseEntity.ok(consultationService.getConsultationsByDate(dateTime));
    }

    // Update consultation
    @PutMapping("/{id}")
    public ResponseEntity<ConsultationResponse> updateConsultation(
            @PathVariable UUID id,
            @RequestBody ConsultationRequest request) {
        return ResponseEntity.ok(consultationService.updateConsultation(id, request));
    }

    // Delete consultation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable UUID id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }

    // Add treatment to consultation
    @PostMapping("/{consultationId}/treatments/{treatmentId}")
    public ResponseEntity<ConsultationResponse> addTreatment(
            @PathVariable UUID consultationId,
            @PathVariable UUID treatmentId) {
        return ResponseEntity.ok(consultationService.addTreatmentToConsultation(consultationId, treatmentId));
    }

    // Remove treatment from consultation
    @DeleteMapping("/{consultationId}/treatments/{treatmentId}")
    public ResponseEntity<ConsultationResponse> removeTreatment(
            @PathVariable UUID consultationId,
            @PathVariable UUID treatmentId) {
        return ResponseEntity.ok(consultationService.removeTreatmentFromConsultation(consultationId, treatmentId));
    }

    // Get all consultations with treatments
    @GetMapping("/with-treatments")
    public ResponseEntity<List<ConsultationResponse>> getConsultationsWithTreatments() {
        return ResponseEntity.ok(consultationService.getConsultationsWithTreatments());
    }

    // Get consultation with full details
    @GetMapping("/{id}/details")
    public ResponseEntity<ConsultationResponse> getConsultationWithDetails(@PathVariable UUID id) {
        return ResponseEntity.ok(consultationService.getConsultationWithDetails(id));
    }
}

