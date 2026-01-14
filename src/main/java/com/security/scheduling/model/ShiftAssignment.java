
package com.security.scheduling.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShiftAssignment {
    private Integer assignmentId;
    private Integer employeeId;
    private Integer postId;
    private Integer shiftId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignmentDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime actualEndTime;

    private String status;
    private BigDecimal overtimeHours;
    private String notes;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Embedded objects for display
    private Employee employee;
    private Post post;
    private Shift shift;


    public ShiftAssignment() {}

    public ShiftAssignment(Integer assignmentId, Integer employeeId, Integer postId, Integer shiftId,
                           LocalDate assignmentDate, LocalDateTime actualStartTime, LocalDateTime actualEndTime,
                           String status, BigDecimal overtimeHours, String notes, LocalDateTime createdAt) {
        this.assignmentId = assignmentId;
        this.employeeId = employeeId;
        this.postId = postId;
        this.shiftId = shiftId;
        this.assignmentDate = assignmentDate;
        this.actualStartTime = actualStartTime;
        this.actualEndTime = actualEndTime;
        this.status = status;
        this.overtimeHours = overtimeHours;
        this.notes = notes;
        this.createdAt = createdAt;
    }


    public Integer getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Integer assignmentId) { this.assignmentId = assignmentId; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Integer getPostId() { return postId; }
    public void setPostId(Integer postId) { this.postId = postId; }

    public Integer getShiftId() { return shiftId; }
    public void setShiftId(Integer shiftId) { this.shiftId = shiftId; }

    public LocalDate getAssignmentDate() { return assignmentDate; }
    public void setAssignmentDate(LocalDate assignmentDate) { this.assignmentDate = assignmentDate; }

    public LocalDateTime getActualStartTime() { return actualStartTime; }
    public void setActualStartTime(LocalDateTime actualStartTime) { this.actualStartTime = actualStartTime; }

    public LocalDateTime getActualEndTime() { return actualEndTime; }
    public void setActualEndTime(LocalDateTime actualEndTime) { this.actualEndTime = actualEndTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getOvertimeHours() { return overtimeHours; }
    public void setOvertimeHours(BigDecimal overtimeHours) { this.overtimeHours = overtimeHours; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }

    public Shift getShift() { return shift; }
    public void setShift(Shift shift) { this.shift = shift; }
}