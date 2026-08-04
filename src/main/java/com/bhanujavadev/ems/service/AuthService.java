package com.bhanujavadev.ems.service;

import com.bhanujavadev.ems.dto.request.LoginRequest;
import com.bhanujavadev.ems.dto.request.RegisterRequest;
import com.bhanujavadev.ems.dto.response.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}