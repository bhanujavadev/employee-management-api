package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.DepartmentRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.DepartmentResponse;
import com.bhanujavadev.ems.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ApiResponse<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        return ApiResponse.<DepartmentResponse>builder()
                .success(true)
                .message("Department created successfully")
                .data(departmentService.createDepartment(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DepartmentResponse> getDepartmentById(@PathVariable Long id) {

        return ApiResponse.<DepartmentResponse>builder()
                .success(true)
                .message("Department fetched successfully")
                .data(departmentService.getDepartmentById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<DepartmentResponse>> getAllDepartments() {

        return ApiResponse.<List<DepartmentResponse>>builder()
                .success(true)
                .message("Departments fetched successfully")
                .data(departmentService.getAllDepartments())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return ApiResponse.<DepartmentResponse>builder()
                .success(true)
                .message("Department updated successfully")
                .data(departmentService.updateDepartment(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDepartment(@PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Department deleted successfully")
                .data("Department Deleted")
                .build();
    }
}