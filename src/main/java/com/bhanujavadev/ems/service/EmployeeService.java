package com.bhanujavadev.ems.service;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);
    Resource downloadPhoto(Long id);

    Resource downloadResume(Long id);

    Page<EmployeeResponse> getAllEmployees(
            EmployeeSearchRequest searchRequest,
            int page,
            int size,
            String sortBy,
            String direction
    );
    Page<EmployeeResponse> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    );

    EmployeeResponse updateEmployee(
            Long id,
            EmployeeRequest request
    );

    void deleteEmployee(Long id);
    EmployeeResponse uploadPhoto(Long employeeId,
                                 org.springframework.web.multipart.MultipartFile file);

    EmployeeResponse uploadResume(Long employeeId,
                                  org.springframework.web.multipart.MultipartFile file);
}


