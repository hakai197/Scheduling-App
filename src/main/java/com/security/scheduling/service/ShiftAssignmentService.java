// ShiftAssignmentService.java
package com.security.scheduling.service;

import com.security.scheduling.dao.ShiftAssignmentDao;
import com.security.scheduling.exception.NotFoundException;
import com.security.scheduling.exception.ServiceException;
import com.security.scheduling.model.ShiftAssignment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShiftAssignmentService {

    private final ShiftAssignmentDao shiftAssignmentDao;

    public ShiftAssignmentService(ShiftAssignmentDao shiftAssignmentDao) {
        this.shiftAssignmentDao = shiftAssignmentDao;
    }

    public List<ShiftAssignment> getAllAssignments() {
        return shiftAssignmentDao.findAll();
    }

    public List<ShiftAssignment> getAssignmentsByEmployee(int employeeId) {
        return shiftAssignmentDao.findByEmployeeId(employeeId);
    }

    public List<ShiftAssignment> getAssignmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return shiftAssignmentDao.findByDateRange(startDate, endDate);
    }

    public ShiftAssignment getAssignmentById(int id) {
        ShiftAssignment assignment = shiftAssignmentDao.findById(id);
        if (assignment == null) {
            throw new NotFoundException("Shift assignment not found with id: " + id);
        }
        return assignment;
    }

    public boolean checkAvailability(int employeeId, LocalDate date, int shiftId) {
        return shiftAssignmentDao.checkAvailability(employeeId, date, shiftId);
    }

    public ShiftAssignment createAssignment(ShiftAssignment assignment) {
        // Validate assignment
        validateAssignment(assignment);

        return shiftAssignmentDao.create(assignment);
    }

    public ShiftAssignment updateAssignment(ShiftAssignment assignment) {
        getAssignmentById(assignment.getAssignmentId()); // Validate existence

        return shiftAssignmentDao.update(assignment);
    }

    public void deleteAssignment(int id) {
        if (!shiftAssignmentDao.delete(id)) {
            throw new NotFoundException("Shift assignment not found with id: " + id);
        }
    }

    public void updateAssignmentStatus(int id, String status) {
        if (!shiftAssignmentDao.updateStatus(id, status)) {
            throw new NotFoundException("Shift assignment not found with id: " + id);
        }
    }

    public ShiftAssignment clockIn(int assignmentId, String location) {
        ShiftAssignment assignment = getAssignmentById(assignmentId);

        // Check if already clocked in
        if (assignment.getActualStartTime() != null) {
            throw new ServiceException("Already clocked in for this assignment");
        }

        assignment.setActualStartTime(LocalDateTime.now());
        assignment.setStatus("IN_PROGRESS");

        return shiftAssignmentDao.update(assignment);
    }

    public ShiftAssignment clockOut(int assignmentId) {
        ShiftAssignment assignment = getAssignmentById(assignmentId);

        // Check if clocked in
        if (assignment.getActualStartTime() == null) {
            throw new ServiceException("Not clocked in for this assignment");
        }

        assignment.setActualEndTime(LocalDateTime.now());
        assignment.setStatus("COMPLETED");

        // Calculate overtime hours if needed
        // This is a simplified calculation
        // In production, you'd calculate based on shift duration vs actual duration

        return shiftAssignmentDao.update(assignment);
    }

    private void validateAssignment(ShiftAssignment assignment) {
        if (assignment.getAssignmentDate() == null) {
            throw new ServiceException("Assignment date is required");
        }

        if (assignment.getAssignmentDate().isBefore(LocalDate.now())) {
            throw new ServiceException("Cannot assign shifts in the past");
        }

        if (assignment.getEmployeeId() == null) {
            throw new ServiceException("Employee ID is required");
        }

        if (assignment.getPostId() == null) {
            throw new ServiceException("Post ID is required");
        }

        if (assignment.getShiftId() == null) {
            throw new ServiceException("Shift ID is required");
        }
    }
}