package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.DesignationResponse;
import com.bhanujavadev.ems.service.DesignationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/designations")
@RequiredArgsConstructor
public class DesignationController {

    private final DesignationService designationService;

    @PostMapping
    public ApiResponse<DesignationResponse> createDesignation(
            @Valid @RequestBody DesignationRequest request) {

        return designationService.createDesignation(request);
    }

    @GetMapping
    public ApiResponse<List<DesignationResponse>> getAllDesignations() {

        return designationService.getAllDesignations();
    }

    @GetMapping("/{id}")
    public ApiResponse<DesignationResponse> getDesignationById(
            @PathVariable Long id) {

        return designationService.getDesignationById(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<DesignationResponse> updateDesignation(
            @PathVariable Long id,
            @Valid @RequestBody DesignationRequest request) {

        return designationService.updateDesignation(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDesignation(
            @PathVariable Long id) {

        return designationService.deleteDesignation(id);
    }
}