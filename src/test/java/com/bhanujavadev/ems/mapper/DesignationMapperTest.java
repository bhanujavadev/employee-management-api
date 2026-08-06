package com.bhanujavadev.ems.mapper;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.DesignationResponse;
import com.bhanujavadev.ems.entity.Designation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DesignationMapperTest {

    private DesignationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new DesignationMapper();
    }

    @Test
    void testToEntity() {

        DesignationRequest request = new DesignationRequest();

        request.setDesignationCode("DES001");
        request.setDesignationName("Java Developer");
        request.setDescription("Backend Development");
        request.setGrade("G5");
        request.setActive(true);

        Designation designation = mapper.toEntity(request);

        assertNotNull(designation);
        assertEquals("DES001", designation.getDesignationCode());
        assertEquals("Java Developer", designation.getDesignationName());
        assertEquals("Backend Development", designation.getDescription());
        assertEquals("G5", designation.getGrade());
        assertTrue(designation.getActive());
    }

    @Test
    void testToResponse() {

        Designation designation = new Designation();

        designation.setId(1L);
        designation.setDesignationCode("DES001");
        designation.setDesignationName("Java Developer");
        designation.setDescription("Backend Development");
        designation.setGrade("G5");
        designation.setActive(true);

        DesignationResponse response = mapper.toResponse(designation);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("DES001", response.getDesignationCode());
        assertEquals("Java Developer", response.getDesignationName());
        assertEquals("Backend Development", response.getDescription());
        assertEquals("G5", response.getGrade());
        assertTrue(response.getActive());
    }

    @Test
    void testUpdateEntity() {

        Designation designation = new Designation();

        DesignationRequest request = new DesignationRequest();

        request.setDesignationCode("DES002");
        request.setDesignationName("Senior Java Developer");
        request.setDescription("Microservices");
        request.setGrade("G6");
        request.setActive(false);

        mapper.updateEntity(designation, request);

        assertEquals("DES002", designation.getDesignationCode());
        assertEquals("Senior Java Developer", designation.getDesignationName());
        assertEquals("Microservices", designation.getDescription());
        assertEquals("G6", designation.getGrade());
        assertFalse(designation.getActive());
    }
}