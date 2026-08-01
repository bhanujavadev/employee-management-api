package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ApiResponse<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return ApiResponse.<EmployeeResponse>builder()
                .success(true)
                .message("Employee created successfully")
                .data(employeeService.createEmployee(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponse> getEmployee(@PathVariable Long id) {

        return ApiResponse.<EmployeeResponse>builder()
                .success(true)
                .message("Employee fetched successfully")
                .data(employeeService.getEmployeeById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<EmployeeResponse>> getAllEmployees() {

        return ApiResponse.<List<EmployeeResponse>>builder()
                .success(true)
                .message("Employees fetched successfully")
                .data(employeeService.getAllEmployees())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ApiResponse.<EmployeeResponse>builder()
                .success(true)
                .message("Employee updated successfully")
                .data(employeeService.updateEmployee(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Employee deleted successfully")
                .data("Deleted")
                .build();
    }

}