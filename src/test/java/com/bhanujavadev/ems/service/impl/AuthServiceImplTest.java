package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.LoginRequest;
import com.bhanujavadev.ems.dto.request.RegisterRequest;
import com.bhanujavadev.ems.dto.response.LoginResponse;
import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.repository.RoleRepository;
import com.bhanujavadev.ems.repository.UserRepository;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.security.service.CustomUserDetails;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

    private Role getRole() {

        return Role.builder()
                .id(1L)
                .roleName("ADMIN")
                .build();
    }

    private User getUser() {

        return User.builder()
                .id(1L)
                .username("bhanu")
                .email("bhanu@gmail.com")
                .password("encodedPassword")
                .firstName("Bhanu")
                .lastName("Priya")
                .active(true)
                .role(getRole())
                .build();
    }

    @Test
    void testRegister() {

        RegisterRequest request = getRegisterRequest();

        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(false);

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(roleRepository.findByRoleName(request.getRole()))
                .thenReturn(Optional.of(getRole()));

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedPassword");

        authService.register(request);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegister_WhenUsernameExists() {

        RegisterRequest request = getRegisterRequest();

        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> authService.register(request));

        assertEquals("Username already exists", ex.getMessage());
    }

    @Test
    void testRegister_WhenEmailExists() {

        RegisterRequest request = getRegisterRequest();

        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(true);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> authService.register(request));

        assertEquals("Email already exists", ex.getMessage());
    }

    @Test
    void testRegister_WhenRoleNotFound() {

        RegisterRequest request = getRegisterRequest();

        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(false);

        when(roleRepository.findByRoleName(anyString()))
                .thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> authService.register(request));

        assertEquals("Role not found", ex.getMessage());
    }

    @Test
    void testLogin() {

        LoginRequest request = getLoginRequest();

        User user = getUser();

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        when(customUserDetailsService.loadUserByUsername("bhanu"))
                .thenReturn(userDetails);

        when(jwtService.generateToken(any(CustomUserDetails.class)))
                .thenReturn("dummy-jwt-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("bhanu", response.getUsername());
        assertEquals("ADMIN", response.getRole());
        assertEquals("dummy-jwt-token", response.getToken());

        verify(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}