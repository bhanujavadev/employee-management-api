package com.bhanujavadev.ems.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank
    private String employeeCode;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String gender;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate joiningDate;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal salary;

    @NotNull
    private Boolean active;

    @NotNull
    private Long departmentId;

    @NotNull
    private Long designationId;

}