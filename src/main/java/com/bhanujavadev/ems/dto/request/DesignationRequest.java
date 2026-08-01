package com.bhanujavadev.ems.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DesignationRequest {

    @NotBlank(message = "Designation code is required")
    private String designationCode;

    @NotBlank(message = "Designation name is required")
    private String designationName;

    private String description;

    private String grade;

    private Boolean active = true;
}