package com.bhanujavadev.ems.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DesignationResponse {

    private Long id;

    private String designationCode;

    private String designationName;

    private String description;

    private String grade;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}