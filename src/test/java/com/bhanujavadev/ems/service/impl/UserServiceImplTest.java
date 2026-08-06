package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.UserRequest;
import com.bhanujavadev.ems.dto.response.UserResponse;
import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.mapper.UserMapper;
import com.bhanujavadev.ems.repository.RoleRepository;
import com.bhanujavadev.ems.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    private UserRequest request;
    private Role role;
    private User user;
    private UserResponse response;

    @BeforeEach
    void setUp() {

        role = Role.builder()
                .id(1L)
                .roleName("ADMIN")
                .build();

        request = new UserRequest();
        request.setUsername("bhanu");
        request.setEmail("bhanu@gmail.com");
        request.setPassword("password");
        request.setFirstName("Bhanu");
        request.setLastName("Priya");
        request.setRoleId(1L);

        user = User.builder()
                .id(1L)
                .username("bhanu")
                .email("bhanu@gmail.com")
                .password("password")
                .firstName("Bhanu")
                .lastName("Priya")
                .active(true)
                .role(role)
                .build();

        response = UserResponse.builder()
                .id(1L)
                .username("bhanu")
                .email("bhanu@gmail.com")
                .firstName("Bhanu")
                .lastName("Priya")
                .roleName("ADMIN")
                .active(true)
                .build();
    }

    @Test
    void testRegisterUser() {

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(userMapper.toEntity(request, role)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.registerUser(request);

        assertNotNull(result);
        assertEquals("bhanu", result.getUsername());

        verify(userRepository).save(user);
    }

    @Test
    void testRegisterUser_UsernameAlreadyExists() {

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(request));

        assertEquals("Username already exists", ex.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> userService.registerUser(request));

        assertEquals("Email already exists", ex.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUser_RoleNotFound() {

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> userService.registerUser(request));

        assertEquals("Role not found", ex.getMessage());
    }

    @Test
    void testGetUserById() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testGetUserById_NotFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> userService.getUserById(1L));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testGetAllUsers() {

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        List<UserResponse> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }

    @Test
    void testUpdateUser() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userService.updateUser(1L, request);

        assertEquals("bhanu", result.getUsername());

        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser_UserNotFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> userService.updateUser(1L, request));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void testUpdateUser_RoleNotFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> userService.updateUser(1L, request));

        assertEquals("Role not found", ex.getMessage());
    }

    @Test
    void testDeleteUser() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> userService.deleteUser(1L));

        assertEquals("User not found", ex.getMessage());

        verify(userRepository, never()).delete(any());
    }
}