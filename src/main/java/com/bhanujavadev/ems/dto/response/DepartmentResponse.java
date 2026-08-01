package com.bhanujavadev.ems.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DepartmentResponse {

    private Long id;

    private String departmentCode;

    private String departmentName;

    private String description;

    private String location;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}