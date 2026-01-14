// EmployeeDao.java
package com.security.scheduling.dao;

import com.security.scheduling.model.Employee;
import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();
    Employee findById(int employeeId);
    Employee findByEmail(String email);
    List<Employee> findByStatus(String status);
    Employee create(Employee employee);
    Employee update(Employee employee);
    boolean delete(int employeeId);
    boolean updateStatus(int employeeId, String status);
}