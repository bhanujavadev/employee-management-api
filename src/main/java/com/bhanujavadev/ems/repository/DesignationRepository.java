package com.bhanujavadev.ems.repository;

import com.bhanujavadev.ems.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesignationRepository extends JpaRepository<Designation, Long> {

    Optional<Designation> findByDesignationCode(String designationCode);

    Optional<Designation> findByDesignationName(String designationName);

    boolean existsByDesignationCode(String designationCode);

    boolean existsByDesignationName(String designationName);

}