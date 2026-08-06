package com.bhanujavadev.ems.security.service;

import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {

        Role role = Role.builder()
                .roleName("ADMIN")
                .build();

        User user = User.builder()
                .username("bhanu")
                .password("password")
                .active(true)
                .role(role)
                .build();

        when(userRepository.findByUsername("bhanu"))
                .thenReturn(Optional.of(user));

        CustomUserDetails result =
                (CustomUserDetails) service.loadUserByUsername("bhanu");

        assertNotNull(result);
        assertEquals("bhanu", result.getUsername());

        verify(userRepository)
                .findByUsername("bhanu");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {

        when(userRepository.findByUsername("bhanu"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("bhanu")
        );

        verify(userRepository)
                .findByUsername("bhanu");
    }
}