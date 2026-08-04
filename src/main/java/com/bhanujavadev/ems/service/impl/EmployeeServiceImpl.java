package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.entity.Department;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.entity.Employee;
import com.bhanujavadev.ems.mapper.EmployeeMapper;
import com.bhanujavadev.ems.repository.DepartmentRepository;
import com.bhanujavadev.ems.repository.DesignationRepository;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import com.bhanujavadev.ems.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final EmployeeMapper employeeMapper;

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
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return employeeRepository.findAll(pageable)
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
}