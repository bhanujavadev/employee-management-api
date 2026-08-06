package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.security.filter.JwtAuthenticationFilter;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import com.bhanujavadev.ems.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    // Security Beans
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private EmployeeRequest getRequest() {

        EmployeeRequest request = new EmployeeRequest();

        request.setEmployeeCode("EMP001");
        request.setFirstName("Bhanu");
        request.setLastName("Priya");
        request.setEmail("bhanu@gmail.com");
        request.setMobileNumber("9876543210");
        request.setGender("Female");
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        request.setJoiningDate(LocalDate.now());
        request.setSalary(BigDecimal.valueOf(50000));
        request.setDepartmentId(1L);
        request.setDesignationId(1L);
        request.setActive(true);

        return request;
    }

    private EmployeeResponse getResponse() {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(1L);
        response.setEmployeeCode("EMP001");
        response.setFirstName("Bhanu");
        response.setLastName("Priya");
        response.setEmail("bhanu@gmail.com");
        response.setMobileNumber("9876543210");
        response.setDepartmentName("IT");
        response.setDesignationName("Developer");
        response.setSalary(BigDecimal.valueOf(50000));
        response.setActive(true);

        return response;
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateEmployee() throws Exception {

        when(employeeService.createEmployee(any(EmployeeRequest.class)))
                .thenReturn(getResponse());

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "HR"})
    void testGetEmployeeById() throws Exception {

        when(employeeService.getEmployeeById(1L))
                .thenReturn(getResponse());

        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.employeeCode").value("EMP001"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "HR"})
    void testGetAllEmployees() throws Exception {

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(getResponse()));

        when(employeeService.getAllEmployees(
                any(EmployeeSearchRequest.class),
                anyInt(),
                anyInt(),
                anyString(),
                anyString()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateEmployee() throws Exception {

        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequest.class)))
                .thenReturn(getResponse());

        mockMvc.perform(put("/api/v1/employees/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.employeeCode").value("EMP001"));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteEmployee() throws Exception {

        mockMvc.perform(delete("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Employee deleted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
    @Test
    @WithMockUser(roles = {"ADMIN","HR"})
    void testUploadPhoto() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "photo.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "dummy image".getBytes());

        when(employeeService.uploadPhoto(eq(1L), any()))
                .thenReturn(getResponse());

        mockMvc.perform(
                        multipart("/api/v1/employees/1/photo")
                                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
    @Test
    @WithMockUser(roles = {"ADMIN","HR"})
    void testUploadResume() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "resume.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "dummy pdf".getBytes());

        when(employeeService.uploadResume(eq(1L), any()))
                .thenReturn(getResponse());

        mockMvc.perform(
                        multipart("/api/v1/employees/1/resume")
                                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
    @Test
    @WithMockUser(roles = {"ADMIN","HR"})
    void testSearchEmployees() throws Exception {

        Page<EmployeeResponse> page =
                new PageImpl<>(List.of(getResponse()));

        when(employeeService.searchEmployees(
                anyString(),
                anyInt(),
                anyInt(),
                anyString(),
                anyString()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/employees/search")
                        .param("keyword", "Bhanu"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
    @Test
    @WithMockUser(roles = {"ADMIN","HR"})
    void testDownloadPhoto() throws Exception {

        Resource resource = mock(Resource.class);

        when(resource.getFilename())
                .thenReturn("photo.jpg");

        when(employeeService.downloadPhoto(1L))
                .thenReturn(resource);

        mockMvc.perform(get("/api/v1/employees/1/photo"))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        "Content-Disposition",
                        "inline; filename=\"photo.jpg\""));
    }
    @Test
    @WithMockUser(roles = {"ADMIN","HR"})
    void testDownloadResume() throws Exception {

        Resource resource = mock(Resource.class);

        when(resource.getFilename())
                .thenReturn("resume.pdf");

        when(employeeService.downloadResume(1L))
                .thenReturn(resource);

        mockMvc.perform(get("/api/v1/employees/1/resume"))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        "Content-Disposition",
                        "attachment; filename=\"resume.pdf\""));
    }

}