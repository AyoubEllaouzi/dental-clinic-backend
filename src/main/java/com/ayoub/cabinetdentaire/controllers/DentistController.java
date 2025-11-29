package com.ayoub.cabinetdentaire.controllers;


import com.ayoub.cabinetdentaire.dtos.dentist.DentistRequest;
import com.ayoub.cabinetdentaire.dtos.dentist.DentistResponse;
import com.ayoub.cabinetdentaire.enums.Specialization;
import com.ayoub.cabinetdentaire.services.dentist.DentistService;
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
@RequestMapping("/api/dentists")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DentistController {

    private final DentistService dentistService;

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<DentistResponse> createDentist(
            @RequestBody DentistRequest request) {

        return new ResponseEntity<>(dentistService.create(request), HttpStatus.CREATED);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DentistResponse> updateDentist(
            @PathVariable UUID id,
            @RequestBody DentistRequest request) {

        return ResponseEntity.ok(dentistService.update(id, request));
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DentistResponse> getDentist(@PathVariable UUID id) {
        return ResponseEntity.ok(dentistService.getById(id));
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<DentistResponse>> getAll() {
        return ResponseEntity.ok(dentistService.getAll());
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable UUID id) {
        dentistService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ GET BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<DentistResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(dentistService.getByEmail(email));
    }

    // ✅ SEARCH
    @GetMapping("/search")
    public ResponseEntity<Page<DentistResponse>> search(
            @RequestParam String keyword,
            @PageableDefault(size = 5) Pageable pageable) {

        return ResponseEntity.ok(dentistService.search(keyword, pageable));
    }

    // ✅ FILTER BY SPECIALIZATION
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<Page<DentistResponse>> getBySpecialization(
            @PathVariable Specialization specialization,
            @PageableDefault(size = 5) Pageable pageable) {

        return ResponseEntity.ok(
                dentistService.getDentistsBySpecialization(specialization, pageable));
    }

    // ✅ GET BY LICENSE NUMBER
    @GetMapping("/license/{license}")
    public ResponseEntity<DentistResponse> getByLicense(@PathVariable String license) {
        return ResponseEntity.ok(
                dentistService.getDentistByLicenseNumber(license));
    }

    // ✅ UPDATE SCHEDULE
    @PatchMapping("/{id}/schedule")
    public ResponseEntity<DentistResponse> updateSchedule(
            @PathVariable UUID id,
            @RequestBody String schedule) {

        return ResponseEntity.ok(
                dentistService.updateDentistSchedule(id, schedule));
    }

    // ✅ FILTER BY ROLE
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<DentistResponse>> getByRole(@PathVariable String roleName) {
        return ResponseEntity.ok(dentistService.getDentistsByRoleName(roleName));
    }

    // ✅ ENABLE / DISABLE
    @PatchMapping("/{id}/status")
    public ResponseEntity<DentistResponse> changeStatus(
            @PathVariable UUID id,
            @RequestParam boolean enabled) {

        return ResponseEntity.ok(dentistService.changeStatus(id, enabled));
    }

    // ✅ COUNT
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(dentistService.count());
    }
}
