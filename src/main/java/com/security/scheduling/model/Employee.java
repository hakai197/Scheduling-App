
package com.security.scheduling.model;



import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee {
    private Integer employeeId;
    private String employeeName;
    private String email;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    private String employmentStatus;
    private String securityLicenseNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseExpiry;

    private BigDecimal hourlyRate;
    private BigDecimal overtimeRate;
    private Boolean overtimeEligible;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public Employee() {}

    public Employee(Integer employeeId, String employeeName, String email, String phone,
                    LocalDate hireDate, String employmentStatus, String securityLicenseNumber,
                    LocalDate licenseExpiry, BigDecimal hourlyRate, BigDecimal overtimeRate,
                    Boolean overtimeEligible, LocalDateTime createdAt) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.phone = phone;
        this.hireDate = hireDate;
        this.employmentStatus = employmentStatus;
        this.securityLicenseNumber = securityLicenseNumber;
        this.licenseExpiry = licenseExpiry;
        this.hourlyRate = hourlyRate;
        this.overtimeRate = overtimeRate;
        this.overtimeEligible = overtimeEligible;
        this.createdAt = createdAt;
    }


    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getSecurityLicenseNumber() { return securityLicenseNumber; }
    public void setSecurityLicenseNumber(String securityLicenseNumber) { this.securityLicenseNumber = securityLicenseNumber; }

    public LocalDate getLicenseExpiry() { return licenseExpiry; }
    public void setLicenseExpiry(LocalDate licenseExpiry) { this.licenseExpiry = licenseExpiry; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public BigDecimal getOvertimeRate() { return overtimeRate; }
    public void setOvertimeRate(BigDecimal overtimeRate) { this.overtimeRate = overtimeRate; }

    public Boolean getOvertimeEligible() { return overtimeEligible; }
    public void setOvertimeEligible(Boolean overtimeEligible) { this.overtimeEligible = overtimeEligible; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}