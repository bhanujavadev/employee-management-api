package com.bhanujavadev.ems.security.service;

import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {

    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {

        Role role = Role.builder()
                .roleName("ADMIN")
                .build();

        User user = User.builder()
                .username("bhanu")
                .password("password")
                .active(true)
                .role(role)
                .build();

        customUserDetails = new CustomUserDetails(user);
    }

    @Test
    void testGetUsername() {

        assertEquals("bhanu",
                customUserDetails.getUsername());
    }

    @Test
    void testGetPassword() {

        assertEquals("password",
                customUserDetails.getPassword());
    }

    @Test
    void testGetAuthorities() {

        Collection<? extends GrantedAuthority> authorities =
                customUserDetails.getAuthorities();

        assertEquals(1, authorities.size());

        assertTrue(
                authorities.stream()
                        .anyMatch(a ->
                                a.getAuthority().equals("ROLE_ADMIN"))
        );
    }

    @Test
    void testIsEnabled() {

        assertTrue(customUserDetails.isEnabled());
    }

    @Test
    void testIsAccountNonExpired() {

        assertTrue(customUserDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {

        assertTrue(customUserDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {

        assertTrue(customUserDetails.isCredentialsNonExpired());
    }
}