package com.bhanujavadev.ems.service.impl;
import com.bhanujavadev.ems.dto.request.LoginRequest;
import com.bhanujavadev.ems.dto.request.RegisterRequest;
import com.bhanujavadev.ems.dto.response.LoginResponse;
import com.bhanujavadev.ems.entity.Role;
import com.bhanujavadev.ems.entity.User;
import com.bhanujavadev.ems.repository.RoleRepository;
import com.bhanujavadev.ems.repository.UserRepository;
import com.bhanujavadev.ems.security.service.CustomUserDetails;
import com.bhanujavadev.ems.security.service.CustomUserDetailsService;
import com.bhanujavadev.ems.security.jwt.JwtService;
import com.bhanujavadev.ems.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByRoleName(request.getRole())
                .orElseThrow(() ->
                        new EntityNotFoundException("Role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .active(true)
                .role(role)
                .build();

        userRepository.save(user);
    }
    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService
                        .loadUserByUsername(request.getUsername());

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .username(userDetails.getUsername())
                .role(userDetails.getUser().getRole().getRoleName())
                .build();
    }
}