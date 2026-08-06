package com.bhanujavadev.ems.report.service.impl;

import com.bhanujavadev.ems.entity.Department;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.entity.Employee;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Employee getEmployee() {

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
        employee.setSalary(BigDecimal.valueOf(50000));
        employee.setActive(true);
        employee.setDepartment(department);
        employee.setDesignation(designation);

        return employee;
    }

    @Test
    void testExportEmployeesToExcel() {

        when(employeeRepository.findAll())
                .thenReturn(List.of(getEmployee()));

        byte[] result = reportService.exportEmployeesToExcel();

        assertNotNull(result);
        assertTrue(result.length > 0);

        verify(employeeRepository).findAll();
    }

    @Test
    void testExportEmployeesToPdf() {

        when(employeeRepository.findAll())
                .thenReturn(List.of(getEmployee()));

        byte[] result = reportService.exportEmployeesToPdf();

        assertNotNull(result);
        assertTrue(result.length > 0);

        verify(employeeRepository).findAll();
    }

    @Test
    void testExportExcelWithEmptyList() {

        when(employeeRepository.findAll())
                .thenReturn(List.of());

        byte[] result = reportService.exportEmployeesToExcel();

        assertNotNull(result);
        assertTrue(result.length > 0);
    }

    @Test
    void testExportPdfWithEmptyList() {

        when(employeeRepository.findAll())
                .thenReturn(List.of());

        byte[] result = reportService.exportEmployeesToPdf();

        assertNotNull(result);
        assertTrue(result.length > 0);
    }
}