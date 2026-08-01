package com.bhanujavadev.ems.entity;

import com.bhanujavadev.ems.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "designations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Designation extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designation_code", nullable = false, unique = true, length = 20)
    private String designationCode;

    @Column(name = "designation_name", nullable = false, unique = true, length = 100)
    private String designationName;

    @Column(length = 300)
    private String description;

    @Column(length = 30)
    private String grade;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}