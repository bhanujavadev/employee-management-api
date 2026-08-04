package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        return ApiResponse.success(
                employeeService.createEmployee(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ApiResponse<EmployeeResponse> getEmployee(
            @PathVariable Long id) {

        return ApiResponse.success(
                employeeService.getEmployeeById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<ApiResponse<Page<EmployeeResponse>>> getAllEmployees(

            EmployeeSearchRequest searchRequest,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(

                ApiResponse.success(

                        employeeService.getAllEmployees(
                                searchRequest,
                                page,
                                size,
                                sortBy,
                                direction
                        )
                )
        );
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
    public ApiResponse<String> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ApiResponse.success(

                "Employee deleted successfully");
    }
    @PostMapping(
            value = "/{id}/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ApiResponse<EmployeeResponse> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ApiResponse.success(
                employeeService.uploadPhoto(id, file)
        );
    }
    @PostMapping(
            value = "/{id}/resume",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ApiResponse<EmployeeResponse> uploadResume(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        return ApiResponse.success(
                employeeService.uploadResume(id, file)
        );
    }
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ApiResponse<Page<EmployeeResponse>> searchEmployees(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ApiResponse.success(

                employeeService.searchEmployees(
                        keyword,
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
    @GetMapping("/{id}/photo")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable Long id) {

        Resource resource = employeeService.downloadPhoto(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping("/{id}/resume")
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) {

        Resource resource = employeeService.downloadResume(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}