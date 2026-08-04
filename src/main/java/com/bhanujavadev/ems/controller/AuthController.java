package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.LoginRequest;
import com.bhanujavadev.ems.dto.request.RegisterRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.LoginResponse;
import com.bhanujavadev.ems.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.success("User registered successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }
}