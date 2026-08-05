package com.bhanujavadev.ems.dto.request;

import lombok.Data;

@Data
public class EmployeeSearchRequest {

    private String keyword;

    private Long departmentId;

    private Long designationId;

    private Boolean active;
}