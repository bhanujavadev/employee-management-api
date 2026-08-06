package com.bhanujavadev.ems.report.controller;

import com.bhanujavadev.ems.report.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = ReportController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "com\\.bhanujavadev\\.ems\\.security\\..*"
                )
        }
)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportService reportService;

    @Test
    void testExportExcel() throws Exception {

        when(reportService.exportEmployeesToExcel())
                .thenReturn("excel".getBytes());

        mockMvc.perform(get("/api/v1/reports/employees/excel"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Disposition"));
    }

    @Test
    void testExportPdf() throws Exception {

        when(reportService.exportEmployeesToPdf())
                .thenReturn("pdf".getBytes());

        mockMvc.perform(get("/api/v1/reports/employees/pdf"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Disposition"));
    }
}