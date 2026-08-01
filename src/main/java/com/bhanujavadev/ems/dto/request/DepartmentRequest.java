package com.bhanujavadev.ems.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentRequest {

    @NotBlank(message = "Department code is required")
    @Size(max = 20)
    private String departmentCode;

    @NotBlank(message = "Department name is required")
    @Size(max = 100)
    private String departmentName;

    @Size(max = 300)
    private String description;

    @Size(max = 100)
    private String location;

    private Boolean active;

}