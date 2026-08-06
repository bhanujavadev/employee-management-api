package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.DesignationResponse;
import com.bhanujavadev.ems.security.filter.JwtAuthenticationFilter;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import com.bhanujavadev.ems.service.DesignationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DesignationController.class)
@AutoConfigureMockMvc(addFilters = false)
class DesignationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DesignationService designationService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private DesignationRequest getRequest() {

        DesignationRequest request = new DesignationRequest();
        request.setDesignationCode("DES001");
        request.setDesignationName("Java Developer");
        request.setDescription("Backend Developer");
        request.setActive(true);

        return request;
    }

    private DesignationResponse getResponse() {

        return DesignationResponse.builder()
                .id(1L)
                .designationCode("DES001")
                .designationName("Java Developer")
                .description("Backend Developer")
                .active(true)
                .build();
    }

    @Test
    @WithMockUser
    void testCreateDesignation() throws Exception {

        ApiResponse<DesignationResponse> response =
                ApiResponse.success(getResponse());

        when(designationService.createDesignation(any(DesignationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/designations")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser
    void testGetAllDesignations() throws Exception {

        ApiResponse<List<DesignationResponse>> response =
                ApiResponse.success(List.of(getResponse()));

        when(designationService.getAllDesignations())
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/designations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].designationCode").value("DES001"));
    }

    @Test
    @WithMockUser
    void testGetDesignationById() throws Exception {

        ApiResponse<DesignationResponse> response =
                ApiResponse.success(getResponse());

        when(designationService.getDesignationById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/designations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.designationCode").value("DES001"));
    }

    @Test
    @WithMockUser
    void testUpdateDesignation() throws Exception {

        ApiResponse<DesignationResponse> response =
                ApiResponse.success(getResponse());

        when(designationService.updateDesignation(eq(1L), any(DesignationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/designations/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser
    void testDeleteDesignation() throws Exception {

        ApiResponse<String> response = new ApiResponse<>(
                true,
                "Designation deleted successfully",
                "Deleted"
        );

        when(designationService.deleteDesignation(1L))
                .thenReturn(response);

        mockMvc.perform(delete("/api/v1/designations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Designation deleted successfully"))
                .andExpect(jsonPath("$.data")
                        .value("Deleted"));
    }
}