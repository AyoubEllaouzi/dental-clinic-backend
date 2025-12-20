package com.ayoub.cabinetdentaire.controllers;

import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentRequest;
import com.ayoub.cabinetdentaire.dtos.appointment.AppointmentResponse;
import com.ayoub.cabinetdentaire.enums.AppointmentStatus;
import com.ayoub.cabinetdentaire.services.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    private final AppointmentService appointmentService;

    //create appointment
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.createAppointment(request));
    }

    //update appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable UUID id,
            @RequestBody AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, request));
    }

    //get appointments
    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    //get appointment by id
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    //get appointment by dentist id
    @GetMapping("/dentist/{id}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentByDentistId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByDentist(id));
    }

    //get appointment by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByStatus(@PathVariable("status") AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
    }

    // get Upcoming Appointments
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments() {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointments());
    }

    //count Appointments By Status
    @GetMapping("/count/{status}")
    public ResponseEntity<Long> countAppointmentsByStatus(@PathVariable AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.countAppointmentsByStatus(status));
    }

    //cancel appointment
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable UUID id,
            @RequestBody String reason) {
        appointmentService.cancelAppointment(id, reason);
        return ResponseEntity.noContent().build();
    }

    //delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    //get available dentist for appointment
    @GetMapping("available/{id}")
    public ResponseEntity<Boolean> isDentistAvailable(
            @PathVariable UUID id,
            @RequestParam LocalDateTime startTime,
            @RequestParam LocalDateTime endTime) {
        return ResponseEntity.ok(appointmentService.isDentistAvailable(id, startTime, endTime));
    }

    // get Available Slots
    @GetMapping("/slots")
    public ResponseEntity<List<LocalDateTime>> getAvailableSlots(@RequestParam UUID dentistId, @RequestParam LocalDate date ) {
        return ResponseEntity.ok(appointmentService.getAvailableSlots(dentistId, date));
    }
}
