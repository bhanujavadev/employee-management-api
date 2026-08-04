package com.bhanujavadev.ems.mapper;

import com.bhanujavadev.ems.dto.request.UserRequest;
import com.bhanujavadev.ems.dto.response.UserResponse;
import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request, Role role) {

        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .active(true)
                .role(role)
                .build();
    }

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleName(user.getRole().getRoleName())
                .active(user.getActive())
                .build();
    }
}