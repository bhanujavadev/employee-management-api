package com.bhanujavadev.ems.service;

import com.bhanujavadev.ems.dto.request.UserRequest;
import com.bhanujavadev.ems.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}