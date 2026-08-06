package com.bhanujavadev.ems.mapper;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.entity.Department;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

    private EmployeeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EmployeeMapper();
    }

    @Test
    void testToEntity() {

        EmployeeRequest request = new EmployeeRequest();

        request.setEmployeeCode("EMP001");
        request.setFirstName("Bhanu");
        request.setLastName("Priya");
        request.setEmail("bhanu@gmail.com");
        request.setMobileNumber("9876543210");
        request.setGender("Female");
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        request.setJoiningDate(LocalDate.of(2024, 1, 1));
        request.setSalary(BigDecimal.valueOf(50000));
        request.setActive(true);

        Employee employee = mapper.toEntity(request);

        assertNotNull(employee);
        assertEquals("EMP001", employee.getEmployeeCode());
        assertEquals("Bhanu", employee.getFirstName());
        assertEquals("Priya", employee.getLastName());
        assertEquals("bhanu@gmail.com", employee.getEmail());
        assertEquals("9876543210", employee.getMobileNumber());
        assertEquals("Female", employee.getGender());
        assertEquals(LocalDate.of(2000, 1, 1), employee.getDateOfBirth());
        assertEquals(LocalDate.of(2024, 1, 1), employee.getJoiningDate());
        assertEquals(BigDecimal.valueOf(50000), employee.getSalary());
        assertTrue(employee.getActive());
    }

    @Test
    void testToResponse() {

        Department department = new Department();
        department.setDepartmentName("IT");

        Designation designation = new Designation();
        designation.setDesignationName("Java Developer");

        Employee employee = new Employee();

        employee.setId(1L);
        employee.setEmployeeCode("EMP001");
        employee.setFirstName("Bhanu");
        employee.setLastName("Priya");
        employee.setEmail("bhanu@gmail.com");
        employee.setMobileNumber("9876543210");
        employee.setGender("Female");
        employee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        employee.setJoiningDate(LocalDate.of(2024, 1, 1));
        employee.setSalary(BigDecimal.valueOf(50000));
        employee.setActive(true);
        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setPhotoUrl("photo.jpg");
        employee.setResumeUrl("resume.pdf");

        EmployeeResponse response = mapper.toResponse(employee);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("EMP001", response.getEmployeeCode());
        assertEquals("Bhanu", response.getFirstName());
        assertEquals("Priya", response.getLastName());
        assertEquals("bhanu@gmail.com", response.getEmail());
        assertEquals("9876543210", response.getMobileNumber());
        assertEquals("Female", response.getGender());
        assertEquals(LocalDate.of(2000, 1, 1), response.getDateOfBirth());
        assertEquals(LocalDate.of(2024, 1, 1), response.getJoiningDate());
        assertEquals(BigDecimal.valueOf(50000), response.getSalary());
        assertEquals("IT", response.getDepartmentName());
        assertEquals("Java Developer", response.getDesignationName());
        assertEquals("photo.jpg", response.getPhotoUrl());
        assertEquals("resume.pdf", response.getResumeUrl());
        assertTrue(response.getActive());
    }

    @Test
    void testUpdateEntity() {

        Employee employee = new Employee();

        EmployeeRequest request = new EmployeeRequest();

        request.setEmployeeCode("EMP002");
        request.setFirstName("Ravi");
        request.setLastName("Kumar");
        request.setEmail("ravi@gmail.com");
        request.setMobileNumber("9999999999");
        request.setGender("Male");
        request.setDateOfBirth(LocalDate.of(1999, 5, 10));
        request.setJoiningDate(LocalDate.of(2025, 1, 1));
        request.setSalary(BigDecimal.valueOf(70000));
        request.setActive(false);

        mapper.updateEntity(employee, request);

        assertEquals("EMP002", employee.getEmployeeCode());
        assertEquals("Ravi", employee.getFirstName());
        assertEquals("Kumar", employee.getLastName());
        assertEquals("ravi@gmail.com", employee.getEmail());
        assertEquals("9999999999", employee.getMobileNumber());
        assertEquals("Male", employee.getGender());
        assertEquals(LocalDate.of(1999, 5, 10), employee.getDateOfBirth());
        assertEquals(LocalDate.of(2025, 1, 1), employee.getJoiningDate());
        assertEquals(BigDecimal.valueOf(70000), employee.getSalary());
        assertFalse(employee.getActive());
    }
}