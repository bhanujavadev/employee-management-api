package com.bhanujavadev.ems.security.filter;

import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.security.service.CustomUserDetails;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterWithoutAuthorizationHeader() throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterWithValidToken() throws Exception {

        Role role = Role.builder()
                .roleName("ADMIN")
                .build();

        User user = User.builder()
                .username("bhanu")
                .password("123")
                .role(role)
                .active(true)
                .build();

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        MockHttpServletRequest request =
                new MockHttpServletRequest();

        request.addHeader(
                "Authorization",
                "Bearer token");

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        when(jwtService.extractUsername("token"))
                .thenReturn("bhanu");

        when(userDetailsService.loadUserByUsername("bhanu"))
                .thenReturn(userDetails);

        when(jwtService.isTokenValid("token", userDetails))
                .thenReturn(true);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}