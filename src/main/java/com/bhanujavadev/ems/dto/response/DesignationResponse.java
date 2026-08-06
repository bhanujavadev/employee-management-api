package com.bhanujavadev.ems.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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