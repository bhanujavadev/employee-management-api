package com.bhanujavadev.ems.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
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

    private BigDecimal salary;

    private Boolean active;

    private String departmentName;

    private String designationName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}