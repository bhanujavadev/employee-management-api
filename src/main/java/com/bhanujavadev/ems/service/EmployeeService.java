package com.bhanujavadev.ems.service;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(Long id);

    Page<EmployeeResponse> getAllEmployees(
            int page,
            int size,
            String sortBy,
            String direction
    );

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    void deleteEmployee(Long id);
}