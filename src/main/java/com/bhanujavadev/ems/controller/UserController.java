package com.bhanujavadev.ems.controller;

import com.bhanujavadev.ems.dto.request.UserRequest;
import com.bhanujavadev.ems.dto.response.UserResponse;
import com.bhanujavadev.ems.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse registerUser(
            @Valid @RequestBody UserRequest request) {

        return userService.registerUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {

        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);
    }
}