package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.LoginRequest;
import com.bhanujavadev.ems.dto.request.RegisterRequest;
import com.bhanujavadev.ems.dto.response.LoginResponse;
import com.bhanujavadev.ems.security.filter.JwtAuthenticationFilter;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import com.bhanujavadev.ems.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    // Security Beans
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    private RegisterRequest getRegisterRequest() {

        RegisterRequest request = new RegisterRequest();

        request.setUsername("bhanu");
        request.setEmail("bhanu@gmail.com");
        request.setPassword("password123");
        request.setFirstName("Bhanu");
        request.setLastName("Priya");
        request.setRole("ADMIN");

        return request;
    }

    private LoginRequest getLoginRequest() {

        LoginRequest request = new LoginRequest();

        request.setUsername("bhanu");
        request.setPassword("password123");

        return request;
    }

    private LoginResponse getLoginResponse() {

        return LoginResponse.builder()
                .token("dummy-jwt-token")
                .username("bhanu")
                .role("ADMIN")
                .build();
    }

    @Test
    @WithMockUser
    void testRegister() throws Exception {

        doNothing().when(authService)
                .register(any(RegisterRequest.class));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRegisterRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("User registered successfully"));
    }

    @Test
    @WithMockUser
    void testLogin() throws Exception {

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(getLoginResponse());

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getLoginRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.username").value("bhanu"))
                .andExpect(jsonPath("$.data.role").value("ADMIN"))
                .andExpect(jsonPath("$.data.token").value("dummy-jwt-token"));
    }
}