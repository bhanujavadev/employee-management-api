package com.bhanujavadev.ems.mapper;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.DesignationResponse;
import com.bhanujavadev.ems.entity.Designation;
import org.springframework.stereotype.Component;

@Component
public class DesignationMapper {

    public Designation toEntity(DesignationRequest request) {

        return Designation.builder()
                .designationCode(request.getDesignationCode())
                .designationName(request.getDesignationName())
                .description(request.getDescription())
                .grade(request.getGrade())
                .active(request.getActive())
                .build();
    }

    public DesignationResponse toResponse(Designation designation) {

        DesignationResponse response = new DesignationResponse();

        response.setId(designation.getId());
        response.setDesignationCode(designation.getDesignationCode());
        response.setDesignationName(designation.getDesignationName());
        response.setDescription(designation.getDescription());
        response.setGrade(designation.getGrade());
        response.setActive(designation.getActive());
        response.setCreatedAt(designation.getCreatedAt());
        response.setUpdatedAt(designation.getUpdatedAt());

        return response;
    }

    public void updateEntity(Designation designation, DesignationRequest request) {

        designation.setDesignationCode(request.getDesignationCode());
        designation.setDesignationName(request.getDesignationName());
        designation.setDescription(request.getDescription());
        designation.setGrade(request.getGrade());
        designation.setActive(request.getActive());

    }
}