package com.bhanujavadev.ems.security.jwt;

import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.security.service.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private CustomUserDetails userDetails;

    @BeforeEach
    void setUp() {

        jwtService = new JwtService();

        ReflectionTestUtils.setField(
                jwtService,
                "secretKey",
                "VGhpc0lzQVN1cGVyU2VjcmV0S2V5Rm9ySldUQXV0aGVudGljYXRpb25UaGF0SXNMb25nRW5vdWdoMTIzNDU2");

        ReflectionTestUtils.setField(
                jwtService,
                "jwtExpiration",
                86400000L);

        Role role = Role.builder()
                .roleName("ADMIN")
                .build();

        User user = User.builder()
                .username("bhanu")
                .password("password")
                .role(role)
                .active(true)
                .build();

        userDetails = new CustomUserDetails(user);
    }

    @Test
    void testGenerateToken() {

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {

        String token = jwtService.generateToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals("bhanu", username);
    }

    @Test
    void testTokenValid() {

        String token = jwtService.generateToken(userDetails);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testTokenInvalid() {

        Role role = Role.builder()
                .roleName("ADMIN")
                .build();

        User user = User.builder()
                .username("other")
                .password("123")
                .role(role)
                .active(true)
                .build();

        CustomUserDetails otherUser =
                new CustomUserDetails(user);

        String token = jwtService.generateToken(userDetails);

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }
}