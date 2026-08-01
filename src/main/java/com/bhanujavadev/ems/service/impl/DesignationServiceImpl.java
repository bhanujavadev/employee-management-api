package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.DesignationResponse;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.mapper.DesignationMapper;
import com.bhanujavadev.ems.repository.DesignationRepository;
import com.bhanujavadev.ems.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    private final DesignationMapper designationMapper;

    @Override
    public ApiResponse<DesignationResponse> createDesignation(
            DesignationRequest request) {

        Designation designation = designationMapper.toEntity(request);

        designation = designationRepository.save(designation);

        return new ApiResponse<>(
                true,
                "Designation created successfully",
                designationMapper.toResponse(designation)
        );
    }

    @Override
    public ApiResponse<List<DesignationResponse>> getAllDesignations() {

        List<DesignationResponse> list =
                designationRepository.findAll()
                        .stream()
                        .map(designationMapper::toResponse)
                        .toList();

        return new ApiResponse<>(
                true,
                "Designation list fetched successfully",
                list
        );
    }

    @Override
    public ApiResponse<DesignationResponse> getDesignationById(Long id) {

        Designation designation =
                designationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Designation not found"));

        return new ApiResponse<>(
                true,
                "Designation found",
                designationMapper.toResponse(designation)
        );
    }

    @Override
    public ApiResponse<DesignationResponse> updateDesignation(
            Long id,
            DesignationRequest request) {

        Designation designation =
                designationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Designation not found"));

        designationMapper.updateEntity(designation, request);

        designation = designationRepository.save(designation);

        return new ApiResponse<>(
                true,
                "Designation updated successfully",
                designationMapper.toResponse(designation)
        );
    }

    @Override
    public ApiResponse<String> deleteDesignation(Long id) {

        Designation designation =
                designationRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Designation not found"));

        designationRepository.delete(designation);

        return new ApiResponse<>(
                true,
                "Designation deleted successfully",
                "Deleted"
        );
    }
}