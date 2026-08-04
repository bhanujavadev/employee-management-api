package com.bhanujavadev.ems.entity;

import com.bhanujavadev.ems.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true, length = 50)
    private String roleName;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}