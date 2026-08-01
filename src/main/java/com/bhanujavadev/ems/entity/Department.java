package com.bhanujavadev.ems.entity;

import com.bhanujavadev.ems.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_code", nullable = false, unique = true, length = 20)
    private String departmentCode;

    @Column(name = "department_name", nullable = false, unique = true, length = 100)
    private String departmentName;

    @Column(length = 300)
    private String description;

    @Column(length = 100)
    private String location;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}