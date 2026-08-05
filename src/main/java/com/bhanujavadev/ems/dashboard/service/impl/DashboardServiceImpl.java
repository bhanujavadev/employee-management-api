package com.bhanujavadev.ems.dashboard.service.impl;

import com.bhanujavadev.ems.dashboard.dto.DashboardResponse;
import com.bhanujavadev.ems.dashboard.service.DashboardService;
import com.bhanujavadev.ems.repository.DepartmentRepository;
import com.bhanujavadev.ems.repository.DesignationRepository;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;

    @Override
    public DashboardResponse getDashboard() {

        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now();

        return DashboardResponse.builder()
                .totalEmployees(employeeRepository.count())
                .activeEmployees(employeeRepository.countByActiveTrue())
                .inactiveEmployees(employeeRepository.countByActiveFalse())
                .totalDepartments(departmentRepository.count())
                .totalDesignations(designationRepository.count())
                .employeesJoinedThisMonth(
                        employeeRepository.countByJoiningDateBetween(
                                firstDayOfMonth,
                                lastDayOfMonth
                        )
                )
                .build();
    }
}