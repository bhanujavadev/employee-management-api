package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return ApiResponse.success(employeeService.createEmployee(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ApiResponse<EmployeeResponse> getEmployee(@PathVariable Long id) {

        return ApiResponse.success(employeeService.getEmployeeById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> getAllEmployees(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        employeeService.getAllEmployees(
                                page,
                                size,
                                sortBy,
                                direction)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {

        return ApiResponse.success(
                employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ApiResponse.success("Employee deleted successfully");
    }
}