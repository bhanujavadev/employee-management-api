package com.bhanujavadev.ems.mapper;

import com.bhanujavadev.ems.dto.request.DepartmentRequest;
import com.bhanujavadev.ems.dto.response.DepartmentResponse;
import com.bhanujavadev.ems.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentMapperTest {

    private DepartmentMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DepartmentMapper();
    }

    @Test
    void testToEntity() {

        DepartmentRequest request = new DepartmentRequest();

        request.setDepartmentCode("IT");
        request.setDepartmentName("Information Technology");
        request.setDescription("IT Department");
        request.setLocation("Hyderabad");
        request.setActive(true);

        Department department = mapper.toEntity(request);

        assertNotNull(department);
        assertEquals("IT", department.getDepartmentCode());
        assertEquals("Information Technology", department.getDepartmentName());
        assertEquals("IT Department", department.getDescription());
        assertEquals("Hyderabad", department.getLocation());
        assertTrue(department.getActive());
    }

    @Test
    void testToResponse() {

        Department department = new Department();

        department.setId(1L);
        department.setDepartmentCode("IT");
        department.setDepartmentName("Information Technology");
        department.setDescription("IT Department");
        department.setLocation("Hyderabad");
        department.setActive(true);

        DepartmentResponse response = mapper.toResponse(department);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("IT", response.getDepartmentCode());
        assertEquals("Information Technology", response.getDepartmentName());
        assertEquals("IT Department", response.getDescription());
        assertEquals("Hyderabad", response.getLocation());
        assertTrue(response.getActive());
    }

    @Test
    void testUpdateEntity() {

        Department department = new Department();

        DepartmentRequest request = new DepartmentRequest();

        request.setDepartmentCode("HR");
        request.setDepartmentName("Human Resource");
        request.setDescription("HR Department");
        request.setLocation("Bangalore");
        request.setActive(false);

        mapper.updateEntity(department, request);

        assertEquals("HR", department.getDepartmentCode());
        assertEquals("Human Resource", department.getDepartmentName());
        assertEquals("HR Department", department.getDescription());
        assertEquals("Bangalore", department.getLocation());
        assertFalse(department.getActive());
    }
}