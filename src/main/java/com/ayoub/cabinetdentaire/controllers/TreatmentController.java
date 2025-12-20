package com.ayoub.cabinetdentaire.controllers;

import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentRequest;
import com.ayoub.cabinetdentaire.dtos.treatment.TreatmentResponse;
import com.ayoub.cabinetdentaire.enums.TreatmentType;
import com.ayoub.cabinetdentaire.services.treatment.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/treatments")
@CrossOrigin(origins = "http://localhost:4200")
public class TreatmentController {
    private final TreatmentService treatmentService;

    @PostMapping
    public ResponseEntity<TreatmentResponse> createConsultation(@RequestBody TreatmentRequest request) {
        return ResponseEntity.ok(treatmentService.createTreatment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreatmentResponse> getTreatmentById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(treatmentService.getTreatmentById(id));
    }

    @GetMapping()
    public ResponseEntity<List<TreatmentResponse>> getAllTreatments() {
        return ResponseEntity.ok(treatmentService.getAllTreatments());
    }

    @GetMapping("/type")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByType(
            @RequestParam TreatmentType type) {
        return ResponseEntity.ok(treatmentService.getTreatmentsByType(type));
    }

    @GetMapping("/consultation/{id}")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByConsultation(
            @PathVariable UUID id) {
        return ResponseEntity.ok(treatmentService.getTreatmentsByConsultation(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreatmentResponse> updateTreatment(@PathVariable UUID id, @RequestBody TreatmentRequest request) {
        return ResponseEntity.ok(treatmentService.updateTreatment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TreatmentResponse> deleteTreatment(@PathVariable UUID id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<TreatmentResponse>> searchTreatments(
            @RequestParam String keyword) {
        return ResponseEntity.ok(treatmentService.searchTreatments(keyword));
    }

    @GetMapping("/price")
    public ResponseEntity<List<TreatmentResponse>> getTreatmentsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(treatmentService.getTreatmentsByPriceRange(minPrice, maxPrice));
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<TreatmentResponse> updateTreatmentPrice(@PathVariable UUID id, @RequestParam BigDecimal newPrice) {
        return ResponseEntity.ok(treatmentService.updateTreatmentPrice(id, newPrice));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> countTreatmentsByType(
            @RequestParam TreatmentType type) {
        return ResponseEntity.ok(treatmentService.countTreatmentsByType(type));
    }
}
