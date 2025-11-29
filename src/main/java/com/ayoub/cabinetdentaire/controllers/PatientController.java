package com.ayoub.cabinetdentaire.controllers;

import com.ayoub.cabinetdentaire.dtos.patient.PatientRequest;
import com.ayoub.cabinetdentaire.dtos.patient.PatientResponse;
import com.ayoub.cabinetdentaire.services.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    private final PatientService patientService;

    // ✅ CREATE
    @PostMapping
    private ResponseEntity<PatientResponse> createPatient(
            @RequestBody final PatientRequest request) {
        return new ResponseEntity<>(patientService.create(request), HttpStatus.CREATED);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(
            @PathVariable UUID id,
            @RequestBody final PatientRequest request) {
        return ResponseEntity.ok(patientService.update(id, request));
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable final UUID id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<PatientResponse>> getPatients() {
        return ResponseEntity.ok(patientService.getAll());
    }

    // ✅ GET BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<PatientResponse> getPatientByEmail(@PathVariable final String email) {
        return ResponseEntity.ok(patientService.getByEmail(email));
    }

    // ✅ SEARCH
    @GetMapping("/search")
    public ResponseEntity<Page<PatientResponse>> search(
            @RequestParam String keyword,
            @PageableDefault(size = 5) final Pageable pageable) {
        return ResponseEntity.ok(patientService.search(keyword, pageable));
    }

    // ✅ FILTER BY ROLE
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<PatientResponse>> getByRole(@PathVariable String roleName) {
        return ResponseEntity.ok(patientService.getPatientsByRoleName(roleName));
    }

    // ✅ ENABLE / DISABLE
    @PatchMapping("/{id}/status")
    public ResponseEntity<PatientResponse> changeStatus(
            @PathVariable UUID id,
            @RequestParam boolean enabled) {

        return ResponseEntity.ok(patientService.changeStatus(id, enabled));
    }

    // ✅ COUNT
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(patientService.count());
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable final UUID id) {
        patientService.delete(id);
    }
}
