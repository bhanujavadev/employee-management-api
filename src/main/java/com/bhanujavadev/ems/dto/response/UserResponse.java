package com.bhanujavadev.ems.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String roleName;

    private Boolean active;
}