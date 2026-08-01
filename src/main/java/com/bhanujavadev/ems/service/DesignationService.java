package com.bhanujavadev.ems.service;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.DesignationResponse;

import java.util.List;

public interface DesignationService {

    ApiResponse<DesignationResponse> createDesignation(DesignationRequest request);

    ApiResponse<List<DesignationResponse>> getAllDesignations();

    ApiResponse<DesignationResponse> getDesignationById(Long id);

    ApiResponse<DesignationResponse> updateDesignation(Long id,
                                                       DesignationRequest request);

    ApiResponse<String> deleteDesignation(Long id);

}