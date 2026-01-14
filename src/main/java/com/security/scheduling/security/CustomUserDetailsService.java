// CustomUserDetailsService.java - Updated version
package com.security.scheduling.security;

import com.security.scheduling.dao.EmployeeDao;
import com.security.scheduling.model.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeDao employeeDao;

    public CustomUserDetailsService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeDao.findByEmail(email);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new UserPrincipal(employee);
    }
}