package com.bhanujavadev.ems.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private long totalEmployees;

    private long activeEmployees;

    private long inactiveEmployees;

    private long totalDepartments;

    private long totalDesignations;

    private long employeesJoinedThisMonth;

}