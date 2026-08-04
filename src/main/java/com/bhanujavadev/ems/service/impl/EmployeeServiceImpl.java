package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.entity.Department;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.entity.Employee;
import com.bhanujavadev.ems.mapper.EmployeeMapper;
import com.bhanujavadev.ems.repository.DepartmentRepository;
import com.bhanujavadev.ems.repository.DesignationRepository;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import com.bhanujavadev.ems.service.EmployeeService;
import com.bhanujavadev.ems.service.FileStorageService;
import com.bhanujavadev.ems.specification.EmployeeSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeMapper employeeMapper;
    private final FileStorageService fileStorageService;

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new EntityNotFoundException("Designation not found"));

        Employee employee = employeeMapper.toEntity(request);

        employee.setDepartment(department);
        employee.setDesignation(designation);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(
            EmployeeSearchRequest searchRequest,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(
                        EmployeeSpecification.search(searchRequest),
                        pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new EntityNotFoundException("Designation not found"));

        employeeMapper.updateEntity(employee, request);

        employee.setDepartment(department);
        employee.setDesignation(designation);

        return employeeMapper.toResponse(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        employeeRepository.delete(employee);
    }

    @Override
    public EmployeeResponse uploadPhoto(Long employeeId, MultipartFile file) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        String filePath = fileStorageService.uploadPhoto(file);

        employee.setPhoto(filePath);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse uploadResume(Long employeeId, MultipartFile file) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        String filePath = fileStorageService.uploadResume(file);

        employee.setResume(filePath);

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponse(savedEmployee);
    }

    @Override
    public Page<EmployeeResponse> searchEmployees(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository
                .findByEmployeeCodeContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNumberContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        keyword,
                        keyword,
                        pageable
                )
                .map(employeeMapper::toResponse);
    }

    @Override
    public Resource downloadPhoto(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (employee.getPhoto() == null || employee.getPhoto().isBlank()) {
            throw new RuntimeException("Photo not found.");
        }

        return fileStorageService.downloadFile(employee.getPhoto());
    }

    @Override
    public Resource downloadResume(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        if (employee.getResume() == null || employee.getResume().isBlank()) {
            throw new RuntimeException("Resume not found.");
        }

        return fileStorageService.downloadFile(employee.getResume());
    }


}