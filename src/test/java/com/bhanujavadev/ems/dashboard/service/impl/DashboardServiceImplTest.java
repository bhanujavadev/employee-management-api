package com.bhanujavadev.ems.dashboard.service.impl;

import com.bhanujavadev.ems.dashboard.dto.DashboardResponse;
import com.bhanujavadev.ems.repository.DepartmentRepository;
import com.bhanujavadev.ems.repository.DesignationRepository;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DesignationRepository designationRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboard() {

        when(employeeRepository.count()).thenReturn(20L);
        when(employeeRepository.countByActiveTrue()).thenReturn(15L);
        when(employeeRepository.countByActiveFalse()).thenReturn(5L);
        when(departmentRepository.count()).thenReturn(4L);
        when(designationRepository.count()).thenReturn(8L);
        when(employeeRepository.countByJoiningDateBetween(any(), any()))
                .thenReturn(3L);

        DashboardResponse response = dashboardService.getDashboard();

        assertNotNull(response);

        assertEquals(20L, response.getTotalEmployees());
        assertEquals(15L, response.getActiveEmployees());
        assertEquals(5L, response.getInactiveEmployees());
        assertEquals(4L, response.getTotalDepartments());
        assertEquals(8L, response.getTotalDesignations());
        assertEquals(3L, response.getEmployeesJoinedThisMonth());

        verify(employeeRepository).count();
        verify(employeeRepository).countByActiveTrue();
        verify(employeeRepository).countByActiveFalse();
        verify(employeeRepository).countByJoiningDateBetween(any(), any());
        verify(departmentRepository).count();
        verify(designationRepository).count();
    }
}