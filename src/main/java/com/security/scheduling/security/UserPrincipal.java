
package com.security.scheduling.security;

import com.security.scheduling.model.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Employee employee;
    private final List<GrantedAuthority> authorities;

    public UserPrincipal(Employee employee) {
        this.employee = employee;
        this.authorities = determineAuthorities(employee);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        // If you have passwords in your employee table
        // return employee.getPassword();

        // For now, using a placeholder or null
        // In a real app, you'd have password hashing
        return "$2a$10$placeholderhashforauthentication"; // BCrypt placeholder
    }

    @Override
    public String getUsername() {
        return employee.getEmail(); // Using email as username
    }

    public Long getId() {
        return Long.valueOf(employee.getEmployeeId());
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"SUSPENDED".equals(employee.getEmploymentStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equals(employee.getEmploymentStatus());
    }

    private List<GrantedAuthority> determineAuthorities(Employee employee) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Default role for all employees
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Add additional roles based on employee attributes
        // Example: If email contains "admin" or "manager", add those roles
        if (employee.getEmail() != null && employee.getEmail().toLowerCase().contains("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        if (employee.getEmail() != null && employee.getEmail().toLowerCase().contains("manager")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        }

        return authorities;
    }
}