package com.bhanujavadev.ems.dashboard.controller;

import com.bhanujavadev.ems.dashboard.dto.DashboardResponse;
import com.bhanujavadev.ems.dashboard.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = DashboardController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "com\\.bhanujavadev\\.ems\\.security\\..*"
                )
        }
)
class DashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DashboardService dashboardService;

    @Test
    void testGetDashboard() throws Exception {

        DashboardResponse response = DashboardResponse.builder()
                .totalEmployees(20L)
                .activeEmployees(15L)
                .inactiveEmployees(5L)
                .totalDepartments(4L)
                .totalDesignations(8L)
                .employeesJoinedThisMonth(3L)
                .build();

        when(dashboardService.getDashboard()).thenReturn(response);

        mockMvc.perform(get("/api/v1/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalEmployees").value(20))
                .andExpect(jsonPath("$.data.activeEmployees").value(15))
                .andExpect(jsonPath("$.data.inactiveEmployees").value(5))
                .andExpect(jsonPath("$.data.totalDepartments").value(4))
                .andExpect(jsonPath("$.data.totalDesignations").value(8))
                .andExpect(jsonPath("$.data.employeesJoinedThisMonth").value(3));
    }
}
