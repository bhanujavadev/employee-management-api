package com.bhanujavadev.ems.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeResponse {

    private Long id;

    private String employeeCode;

    private String firstName;

    private String lastName;

    private String email;

    private String mobileNumber;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate joiningDate;

    private String designation;

    private BigDecimal salary;

    private Boolean active;

    private String departmentName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}