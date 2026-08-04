package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.UserRequest;
import com.bhanujavadev.ems.dto.response.UserResponse;
import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.mapper.UserMapper;
import com.bhanujavadev.ems.repository.RoleRepository;
import com.bhanujavadev.ems.repository.UserRepository;
import com.bhanujavadev.ems.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse registerUser(UserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        User user = userMapper.toEntity(request, role);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(role);

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        userRepository.delete(user);
    }
}