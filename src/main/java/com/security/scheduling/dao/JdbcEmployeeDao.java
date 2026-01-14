
package com.security.scheduling.dao;

import com.security.scheduling.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcEmployeeDao implements EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEmployeeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee ORDER BY employee_name";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            employees.add(mapRowToEmployee(results));
        }
        return employees;
    }

    @Override
    public Employee findById(int employeeId) {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, employeeId);
        if (results.next()) {
            return mapRowToEmployee(results);
        }
        return null;
    }

    @Override
    public Employee findByEmail(String email) {
        String sql = "SELECT * FROM employee WHERE email = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, email);
        if (results.next()) {
            return mapRowToEmployee(results);
        }
        return null;
    }

    @Override
    public List<Employee> findByStatus(String status) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee WHERE employment_status = ? ORDER BY employee_name";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, status);
        while (results.next()) {
            employees.add(mapRowToEmployee(results));
        }
        return employees;
    }

    @Override
    public Employee create(Employee employee) {
        String sql = "INSERT INTO employee (employee_name, email, phone, hire_date, " +
                "employment_status, security_license_number, license_expiry, " +
                "hourly_rate, overtime_rate, overtime_eligible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING employee_id";

        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                employee.getEmployeeName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                employee.getEmploymentStatus(),
                employee.getSecurityLicenseNumber(),
                employee.getLicenseExpiry(),
                employee.getHourlyRate(),
                employee.getOvertimeRate(),
                employee.getOvertimeEligible());

        return findById(newId);
    }

    @Override
    public Employee update(Employee employee) {
        String sql = "UPDATE employee SET employee_name = ?, email = ?, phone = ?, " +
                "hire_date = ?, employment_status = ?, security_license_number = ?, " +
                "license_expiry = ?, hourly_rate = ?, overtime_rate = ?, " +
                "overtime_eligible = ? WHERE employee_id = ?";

        jdbcTemplate.update(sql,
                employee.getEmployeeName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                employee.getEmploymentStatus(),
                employee.getSecurityLicenseNumber(),
                employee.getLicenseExpiry(),
                employee.getHourlyRate(),
                employee.getOvertimeRate(),
                employee.getOvertimeEligible(),
                employee.getEmployeeId());

        return findById(employee.getEmployeeId());
    }

    @Override
    public boolean delete(int employeeId) {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        return jdbcTemplate.update(sql, employeeId) > 0;
    }

    @Override
    public boolean updateStatus(int employeeId, String status) {
        String sql = "UPDATE employee SET employment_status = ? WHERE employee_id = ?";
        return jdbcTemplate.update(sql, status, employeeId) > 0;
    }

    private Employee mapRowToEmployee(SqlRowSet rs) {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("employee_id"));
        employee.setEmployeeName(rs.getString("employee_name"));
        employee.setEmail(rs.getString("email"));
        employee.setPhone(rs.getString("phone"));

        if (rs.getDate("hire_date") != null) {
            employee.setHireDate(rs.getDate("hire_date").toLocalDate());
        }

        employee.setEmploymentStatus(rs.getString("employment_status"));
        employee.setSecurityLicenseNumber(rs.getString("security_license_number"));

        if (rs.getDate("license_expiry") != null) {
            employee.setLicenseExpiry(rs.getDate("license_expiry").toLocalDate());
        }

        employee.setHourlyRate(rs.getBigDecimal("hourly_rate"));
        employee.setOvertimeRate(rs.getBigDecimal("overtime_rate"));
        employee.setOvertimeEligible(rs.getBoolean("overtime_eligible"));

        if (rs.getTimestamp("created_at") != null) {
            employee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }

        return employee;
    }
}