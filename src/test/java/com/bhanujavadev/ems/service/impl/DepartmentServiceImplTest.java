package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.DepartmentRequest;
import com.bhanujavadev.ems.dto.response.DepartmentResponse;
import com.bhanujavadev.ems.entity.Department;
import com.bhanujavadev.ems.exception.custom.ResourceNotFoundException;
import com.bhanujavadev.ems.mapper.DepartmentMapper;
import com.bhanujavadev.ems.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepartmentServiceImplTest {

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    DepartmentMapper departmentMapper;

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private DepartmentRequest getRequest() {

        DepartmentRequest request = new DepartmentRequest();
        request.setDepartmentCode("DEP001");
        request.setDepartmentName("IT");
        return request;
    }

    private Department getDepartment() {

        Department department = new Department();
        department.setId(1L);
        department.setDepartmentCode("DEP001");
        department.setDepartmentName("IT");
        return department;
    }

    private DepartmentResponse getResponse() {

        DepartmentResponse response = new DepartmentResponse();
        response.setId(1L);
        response.setDepartmentCode("DEP001");
        response.setDepartmentName("IT");
        return response;
    }

    @Test
    void testCreateDepartment() {

        when(departmentRepository.existsByDepartmentCode(anyString())).thenReturn(false);
        when(departmentRepository.existsByDepartmentName(anyString())).thenReturn(false);
        when(departmentMapper.toEntity(any())).thenReturn(getDepartment());
        when(departmentRepository.save(any())).thenReturn(getDepartment());
        when(departmentMapper.toResponse(any())).thenReturn(getResponse());

        DepartmentResponse response = departmentService.createDepartment(getRequest());

        assertNotNull(response);
        assertEquals("DEP001", response.getDepartmentCode());
    }

    @Test
    void testGetDepartmentById() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(getDepartment()));
        when(departmentMapper.toResponse(any())).thenReturn(getResponse());

        DepartmentResponse response = departmentService.getDepartmentById(1L);

        assertEquals(1L, response.getId());
    }

    @Test
    void testGetAllDepartments() {

        when(departmentRepository.findAll()).thenReturn(List.of(getDepartment()));
        when(departmentMapper.toResponse(any())).thenReturn(getResponse());

        List<DepartmentResponse> list = departmentService.getAllDepartments();

        assertEquals(1, list.size());
    }

    @Test
    void testUpdateDepartment() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(getDepartment()));
        when(departmentRepository.save(any())).thenReturn(getDepartment());
        when(departmentMapper.toResponse(any())).thenReturn(getResponse());

        DepartmentResponse response =
                departmentService.updateDepartment(1L, getRequest());

        assertEquals("IT", response.getDepartmentName());

        verify(departmentMapper).updateEntity(any(), any());
    }

    @Test
    void testDeleteDepartment() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.of(getDepartment()));

        departmentService.deleteDepartment(1L);

        verify(departmentRepository).delete(any());
    }

    @Test
    void testDepartmentNotFound() {

        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> departmentService.getDepartmentById(1L));
    }

    @Test
    void testDepartmentCodeAlreadyExists() {

        when(departmentRepository.existsByDepartmentCode(anyString()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> departmentService.createDepartment(getRequest()));
    }

    @Test
    void testDepartmentNameAlreadyExists() {

        when(departmentRepository.existsByDepartmentCode(anyString()))
                .thenReturn(false);

        when(departmentRepository.existsByDepartmentName(anyString()))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> departmentService.createDepartment(getRequest()));
    }
}