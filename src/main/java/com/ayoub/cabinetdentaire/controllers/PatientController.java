package com.ayoub.cabinetdentaire.controllers;

import com.ayoub.cabinetdentaire.dtos.PatientRequest;
import com.ayoub.cabinetdentaire.dtos.PatientResponse;
import com.ayoub.cabinetdentaire.services.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    // ✅ CREATE
    @PostMapping
    private ResponseEntity<PatientResponse> createPatient(
            @RequestBody final PatientRequest request) {
        return new ResponseEntity<>(patientService.create(request), HttpStatus.CREATED);
    }
}
