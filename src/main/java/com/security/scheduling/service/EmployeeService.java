
package com.security.scheduling.service;

import com.security.scheduling.dao.EmployeeDao;
import com.security.scheduling.exception.NotFoundException;
import com.security.scheduling.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public List<Employee> getActiveEmployees() {
        return employeeDao.findByStatus("ACTIVE");
    }

    public Employee getEmployeeById(int id) {
        Employee employee = employeeDao.findById(id);
        if (employee == null) {
            throw new NotFoundException("Employee not found with id: " + id);
        }
        return employee;
    }

    public Employee createEmployee(Employee employee) {
        // Set default values
        if (employee.getEmploymentStatus() == null) {
            employee.setEmploymentStatus("ACTIVE");
        }
        if (employee.getOvertimeEligible() == null) {
            employee.setOvertimeEligible(true);
        }
        if (employee.getOvertimeRate() == null && employee.getHourlyRate() != null) {
            employee.setOvertimeRate(employee.getHourlyRate().multiply(new java.math.BigDecimal("1.5")));
        }

        return employeeDao.create(employee);
    }

    public Employee updateEmployee(Employee employee) {
        Employee existing = getEmployeeById(employee.getEmployeeId());

        // Preserve some fields if not provided
        if (employee.getHireDate() == null) {
            employee.setHireDate(existing.getHireDate());
        }
        if (employee.getCreatedAt() == null) {
            employee.setCreatedAt(existing.getCreatedAt());
        }

        return employeeDao.update(employee);
    }

    public void deleteEmployee(int id) {
        if (!employeeDao.delete(id)) {
            throw new NotFoundException("Employee not found with id: " + id);
        }
    }

    public void updateEmployeeStatus(int id, String status) {
        if (!employeeDao.updateStatus(id, status)) {
            throw new NotFoundException("Employee not found with id: " + id);
        }
    }
}