// ShiftAssignmentDao.java
package com.security.scheduling.dao;

import com.security.scheduling.model.ShiftAssignment;
import java.time.LocalDate;
import java.util.List;

public interface ShiftAssignmentDao {
    List<ShiftAssignment> findAll();
    ShiftAssignment findById(int assignmentId);
    List<ShiftAssignment> findByEmployeeId(int employeeId);
    List<ShiftAssignment> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<ShiftAssignment> findByEmployeeAndDate(int employeeId, LocalDate date);
    ShiftAssignment create(ShiftAssignment assignment);
    ShiftAssignment update(ShiftAssignment assignment);
    boolean delete(int assignmentId);
    boolean updateStatus(int assignmentId, String status);
    boolean checkAvailability(int employeeId, LocalDate date, int shiftId);
}