package com.bhanujavadev.ems.dashboard.controller;

import com.bhanujavadev.ems.dashboard.dto.DashboardResponse;
import com.bhanujavadev.ems.dashboard.service.DashboardService;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public ApiResponse<DashboardResponse> getDashboard() {

        return ApiResponse.success(
                dashboardService.getDashboard()
        );
    }
}