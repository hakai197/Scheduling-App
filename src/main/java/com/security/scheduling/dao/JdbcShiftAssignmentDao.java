// JdbcShiftAssignmentDao.java
package com.security.scheduling.dao;

import com.security.scheduling.exception.DaoException;
import com.security.scheduling.model.Employee;
import com.security.scheduling.model.Post;
import com.security.scheduling.model.Shift;
import com.security.scheduling.model.ShiftAssignment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcShiftAssignmentDao implements ShiftAssignmentDao {

    private final JdbcTemplate jdbcTemplate;
    private final EmployeeDao employeeDao;
    private final PostDao postDao;
    private final ShiftDao shiftDao;

    public JdbcShiftAssignmentDao(JdbcTemplate jdbcTemplate, EmployeeDao employeeDao,
                                  PostDao postDao, ShiftDao shiftDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.employeeDao = employeeDao;
        this.postDao = postDao;
        this.shiftDao = shiftDao;
    }

    @Override
    public List<ShiftAssignment> findAll() {
        List<ShiftAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM shift_assignment ORDER BY assignment_date DESC, shift_id";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            assignments.add(mapRowToShiftAssignment(results));
        }
        return assignments;
    }

    @Override
    public ShiftAssignment findById(int assignmentId) {
        String sql = "SELECT * FROM shift_assignment WHERE assignment_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, assignmentId);
        if (results.next()) {
            return mapRowToShiftAssignment(results);
        }
        return null;
    }

    @Override
    public List<ShiftAssignment> findByEmployeeId(int employeeId) {
        List<ShiftAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM shift_assignment WHERE employee_id = ? " +
                "ORDER BY assignment_date DESC";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, employeeId);
        while (results.next()) {
            assignments.add(mapRowToShiftAssignment(results));
        }
        return assignments;
    }

    @Override
    public List<ShiftAssignment> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<ShiftAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM shift_assignment " +
                "WHERE assignment_date BETWEEN ? AND ? " +
                "ORDER BY assignment_date, shift_id";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, startDate, endDate);
        while (results.next()) {
            assignments.add(mapRowToShiftAssignment(results));
        }
        return assignments;
    }

    @Override
    public List<ShiftAssignment> findByEmployeeAndDate(int employeeId, LocalDate date) {
        List<ShiftAssignment> assignments = new ArrayList<>();
        String sql = "SELECT * FROM shift_assignment " +
                "WHERE employee_id = ? AND assignment_date = ? " +
                "ORDER BY shift_id";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, employeeId, date);
        while (results.next()) {
            assignments.add(mapRowToShiftAssignment(results));
        }
        return assignments;
    }

    @Override
    public ShiftAssignment create(ShiftAssignment assignment) {
        // Check availability first
        if (!checkAvailability(assignment.getEmployeeId(),
                assignment.getAssignmentDate(),
                assignment.getShiftId())) {
            throw new DaoException("Employee already has a shift assigned for this date and shift");
        }

        String sql = "INSERT INTO shift_assignment (employee_id, post_id, shift_id, " +
                "assignment_date, actual_start_time, actual_end_time, status, " +
                "overtime_hours, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING assignment_id";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                assignment.getEmployeeId(),
                assignment.getPostId(),
                assignment.getShiftId(),
                assignment.getAssignmentDate(),
                assignment.getActualStartTime(),
                assignment.getActualEndTime(),
                assignment.getStatus() != null ? assignment.getStatus() : "SCHEDULED",
                assignment.getOvertimeHours(),
                assignment.getNotes());

        return findById(newId);
    }

    @Override
    public ShiftAssignment update(ShiftAssignment assignment) {
        String sql = "UPDATE shift_assignment SET " +
                "employee_id = ?, post_id = ?, shift_id = ?, " +
                "assignment_date = ?, actual_start_time = ?, actual_end_time = ?, " +
                "status = ?, overtime_hours = ?, notes = ? " +
                "WHERE assignment_id = ?";

        jdbcTemplate.update(sql,
                assignment.getEmployeeId(),
                assignment.getPostId(),
                assignment.getShiftId(),
                assignment.getAssignmentDate(),
                assignment.getActualStartTime(),
                assignment.getActualEndTime(),
                assignment.getStatus(),
                assignment.getOvertimeHours(),
                assignment.getNotes(),
                assignment.getAssignmentId());

        return findById(assignment.getAssignmentId());
    }

    @Override
    public boolean delete(int assignmentId) {
        String sql = "DELETE FROM shift_assignment WHERE assignment_id = ?";
        return jdbcTemplate.update(sql, assignmentId) > 0;
    }

    @Override
    public boolean updateStatus(int assignmentId, String status) {
        String sql = "UPDATE shift_assignment SET status = ? WHERE assignment_id = ?";
        return jdbcTemplate.update(sql, status, assignmentId) > 0;
    }

    @Override
    public boolean checkAvailability(int employeeId, LocalDate date, int shiftId) {
        String sql = "SELECT COUNT(*) FROM shift_assignment " +
                "WHERE employee_id = ? AND assignment_date = ? AND shift_id = ? " +
                "AND status NOT IN ('CANCELLED')";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                employeeId, date, shiftId);
        return count == 0;
    }

    private ShiftAssignment mapRowToShiftAssignment(SqlRowSet rs) {
        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setAssignmentId(rs.getInt("assignment_id"));
        assignment.setEmployeeId(rs.getInt("employee_id"));
        assignment.setPostId(rs.getInt("post_id"));
        assignment.setShiftId(rs.getInt("shift_id"));

        if (rs.getDate("assignment_date") != null) {
            assignment.setAssignmentDate(rs.getDate("assignment_date").toLocalDate());
        }

        if (rs.getTimestamp("actual_start_time") != null) {
            assignment.setActualStartTime(rs.getTimestamp("actual_start_time").toLocalDateTime());
        }

        if (rs.getTimestamp("actual_end_time") != null) {
            assignment.setActualEndTime(rs.getTimestamp("actual_end_time").toLocalDateTime());
        }

        assignment.setStatus(rs.getString("status"));
        assignment.setOvertimeHours(rs.getBigDecimal("overtime_hours"));
        assignment.setNotes(rs.getString("notes"));

        if (rs.getTimestamp("created_at") != null) {
            assignment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }

        // Load related objects
        assignment.setEmployee(employeeDao.findById(assignment.getEmployeeId()));
        assignment.setPost(postDao.findById(assignment.getPostId()));
        assignment.setShift(shiftDao.findById(assignment.getShiftId()));

        return assignment;
    }
}