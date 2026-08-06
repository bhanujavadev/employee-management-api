package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.DepartmentRequest;
import com.bhanujavadev.ems.dto.response.DepartmentResponse;
import com.bhanujavadev.ems.security.filter.JwtAuthenticationFilter;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import com.bhanujavadev.ems.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class DepartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DepartmentService departmentService;

    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    JwtService jwtService;

    @MockitoBean
    CustomUserDetailsService customUserDetailsService;
    @MockitoBean
    DepartmentResponse departmentResponse;

    private DepartmentRequest getRequest() {

        DepartmentRequest request = new DepartmentRequest();
        request.setDepartmentCode("DEP001");
        request.setDepartmentName("IT");

        return request;
    }

    private DepartmentResponse getResponse() {

        DepartmentResponse response = new DepartmentResponse();
        response.setId(1L);
        response.setDepartmentCode("DEP001");
        response.setDepartmentName("IT");

        return response;
    }

    @Test
    @WithMockUser
    void testCreateDepartment() throws Exception {

        when(departmentService.createDepartment(any(DepartmentRequest.class)))
                .thenReturn(getResponse());

        mockMvc.perform(post("/api/v1/departments")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department created successfully"))
                .andExpect(jsonPath("$.data.departmentCode").value("DEP001"));
    }

    @Test
    @WithMockUser
    void testGetDepartmentById() throws Exception {

        when(departmentService.getDepartmentById(1L))
                .thenReturn(getResponse());

        mockMvc.perform(get("/api/v1/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department fetched successfully"))
                .andExpect(jsonPath("$.data.departmentName").value("IT"));
    }

    @Test
    @WithMockUser
    void testGetAllDepartments() throws Exception {

        when(departmentService.getAllDepartments())
                .thenReturn(List.of(getResponse()));

        mockMvc.perform(get("/api/v1/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].departmentCode").value("DEP001"));
    }

    @Test
    @WithMockUser
    void testUpdateDepartment() throws Exception {

        when(departmentService.updateDepartment(eq(1L), any(DepartmentRequest.class)))
                .thenReturn(getResponse());

        mockMvc.perform(put("/api/v1/departments/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department updated successfully"))
                .andExpect(jsonPath("$.data.departmentName").value("IT"));
    }

    @Test
    @WithMockUser
    void testDeleteDepartment() throws Exception {

        doNothing().when(departmentService).deleteDepartment(1L);

        mockMvc.perform(delete("/api/v1/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Department deleted successfully"))
                .andExpect(jsonPath("$.data").value("Department Deleted"));
    }
}