
package com.security.scheduling.controller;

import com.security.scheduling.model.ShiftAssignment;
import com.security.scheduling.service.ShiftAssignmentService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shift-assignments")
@CrossOrigin
public class ShiftAssignmentController {

    private final ShiftAssignmentService shiftAssignmentService;

    public ShiftAssignmentController(ShiftAssignmentService shiftAssignmentService) {
        this.shiftAssignmentService = shiftAssignmentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<List<ShiftAssignment>> getAllAssignments() {
        List<ShiftAssignment> assignments = shiftAssignmentService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ShiftAssignment>> getAssignmentsByEmployee(
            @PathVariable int employeeId) {
        List<ShiftAssignment> assignments = shiftAssignmentService.getAssignmentsByEmployee(employeeId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ShiftAssignment>> getAssignmentsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ShiftAssignment> assignments = shiftAssignmentService.getAssignmentsByDateRange(startDate, endDate);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam int employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int shiftId) {
        boolean isAvailable = shiftAssignmentService.checkAvailability(employeeId, date, shiftId);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ShiftAssignment> createAssignment(@Valid @RequestBody ShiftAssignment assignment) {
        ShiftAssignment createdAssignment = shiftAssignmentService.createAssignment(assignment);
        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ShiftAssignment> updateAssignment(@PathVariable int id,
                                                            @Valid @RequestBody ShiftAssignment assignment) {
        assignment.setAssignmentId(id);
        ShiftAssignment updatedAssignment = shiftAssignmentService.updateAssignment(assignment);
        return ResponseEntity.ok(updatedAssignment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable int id) {
        shiftAssignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<Void> updateAssignmentStatus(@PathVariable int id,
                                                       @RequestParam String status) {
        shiftAssignmentService.updateAssignmentStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/clock-in")
    public ResponseEntity<ShiftAssignment> clockIn(@PathVariable int id,
                                                   @RequestParam String location) {
        ShiftAssignment assignment = shiftAssignmentService.clockIn(id, location);
        return ResponseEntity.ok(assignment);
    }

    @PatchMapping("/{id}/clock-out")
    public ResponseEntity<ShiftAssignment> clockOut(@PathVariable int id) {
        ShiftAssignment assignment = shiftAssignmentService.clockOut(id);
        return ResponseEntity.ok(assignment);
    }
}