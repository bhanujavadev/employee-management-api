

package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.DesignationRequest;
import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.dto.response.DesignationResponse;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.mapper.DesignationMapper;
import com.bhanujavadev.ems.repository.DesignationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class
DesignationServiceImplTest {

    @Mock
    private DesignationRepository designationRepository;

    @Mock
    private DesignationMapper designationMapper;


    @InjectMocks
    private DesignationServiceImpl designationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private DesignationRequest getRequest() {

        DesignationRequest request = new DesignationRequest();
        request.setDesignationCode("DES001");
        request.setDesignationName("Java Developer");
        request.setDescription("Backend");
        request.setGrade("G5");
        request.setActive(true);

        return request;
    }

    private Designation getDesignation() {

        return Designation.builder()
                .id(1L)
                .designationCode("DES001")
                .designationName("Java Developer")
                .description("Backend")
                .grade("G5")
                .active(true)
                .build();
    }

    private DesignationResponse getResponse() {

        return DesignationResponse.builder()
                .id(1L)
                .designationCode("DES001")
                .designationName("Java Developer")
                .description("Backend")
                .grade("G5")
                .active(true)
                .build();
    }

    @Test
    void testCreateDesignation() {

        Designation designation = getDesignation();
        DesignationResponse response = getResponse();

        when(designationMapper.toEntity(any(DesignationRequest.class)))
                .thenReturn(designation);

        when(designationRepository.save(designation))
                .thenReturn(designation);

        when(designationMapper.toResponse(designation))
                .thenReturn(response);

        ApiResponse<DesignationResponse> result =
                designationService.createDesignation(getRequest());

        assertTrue(result.isSuccess());
        assertEquals("Designation created successfully", result.getMessage());
        assertEquals("DES001", result.getData().getDesignationCode());

        verify(designationRepository).save(designation);
    }

    @Test
    void testGetAllDesignations() {

        Designation designation = getDesignation();
        DesignationResponse response = getResponse();

        when(designationRepository.findAll())
                .thenReturn(List.of(designation));

        when(designationMapper.toResponse(designation))
                .thenReturn(response);

        ApiResponse<List<DesignationResponse>> result =
                designationService.getAllDesignations();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getData().size());

        verify(designationRepository).findAll();
    }

    @Test
    void testGetDesignationById() {

        Designation designation = getDesignation();
        DesignationResponse response = getResponse();

        when(designationRepository.findById(1L))
                .thenReturn(Optional.of(designation));

        when(designationMapper.toResponse(designation))
                .thenReturn(response);

        ApiResponse<DesignationResponse> result =
                designationService.getDesignationById(1L);

        assertTrue(result.isSuccess());
        assertEquals(1L, result.getData().getId());

        verify(designationRepository).findById(1L);
    }

    @Test
    void testGetDesignationById_NotFound() {

        when(designationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> designationService.getDesignationById(1L));

        assertEquals("Designation not found", ex.getMessage());
    }

    @Test
    void testUpdateDesignation() {

        Designation designation = getDesignation();
        DesignationResponse response = getResponse();

        when(designationRepository.findById(1L))
                .thenReturn(Optional.of(designation));

        when(designationRepository.save(designation))
                .thenReturn(designation);

        when(designationMapper.toResponse(designation))
                .thenReturn(response);

        ApiResponse<DesignationResponse> result =
                designationService.updateDesignation(1L, getRequest());

        assertTrue(result.isSuccess());
        assertEquals("Designation updated successfully", result.getMessage());

        verify(designationMapper).updateEntity(eq(designation), any(DesignationRequest.class));
        verify(designationRepository).save(designation);
    }

    @Test
    void testUpdateDesignation_NotFound() {

        when(designationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> designationService.updateDesignation(1L, getRequest()));

        assertEquals("Designation not found", ex.getMessage());
    }

    @Test
    void testDeleteDesignation() {

        Designation designation = getDesignation();

        when(designationRepository.findById(1L))
                .thenReturn(Optional.of(designation));

        ApiResponse<String> result =
                designationService.deleteDesignation(1L);

        assertTrue(result.isSuccess());
        assertEquals("Deleted", result.getData());

        verify(designationRepository).delete(designation);
    }

    @Test
    void testDeleteDesignation_NotFound() {

        when(designationRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> designationService.deleteDesignation(1L));

        assertEquals("Designation not found", ex.getMessage());
    }
}