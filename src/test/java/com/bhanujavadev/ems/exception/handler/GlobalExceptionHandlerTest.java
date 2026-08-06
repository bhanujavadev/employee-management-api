package com.bhanujavadev.ems.exception.handler;

import com.bhanujavadev.ems.dto.response.ApiResponse;
import com.bhanujavadev.ems.exception.custom.DuplicateResourceException;
import com.bhanujavadev.ems.exception.custom.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleResourceNotFound() {

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleResourceNotFound(
                        new ResourceNotFoundException("Employee not found"));

        assertEquals(404, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Employee not found", response.getBody().getMessage());
    }

    @Test
    void testHandleDuplicateResource() {

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleDuplicateResource(
                        new DuplicateResourceException("Already exists"));

        assertEquals(409, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Already exists", response.getBody().getMessage());
    }

    @Test
    void testHandleEntityNotFound() {

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleEntityNotFound(
                        new EntityNotFoundException("Role not found"));

        assertEquals(404, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Role not found", response.getBody().getMessage());
    }

    @Test
    void testHandleIllegalArgument() {

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleIllegalArgument(
                        new IllegalArgumentException("Invalid"));

        assertEquals(400, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Invalid", response.getBody().getMessage());
    }

    @Test
    void testHandleAccessDenied() {

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleAccessDenied(
                        new AccessDeniedException("Denied"));

        assertEquals(403, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Access Denied", response.getBody().getMessage());
    }

    @Test
    void testHandleValidation() throws Exception {

        Dummy dummy = new Dummy();

        BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(dummy, "dummy");

        bindingResult.addError(
                new FieldError(
                        "dummy",
                        "name",
                        "Name is required"));

        Method method =
                Dummy.class.getDeclaredMethod("dummyMethod", String.class);

        MethodParameter methodParameter =
                new MethodParameter(method, 0);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(
                        methodParameter,
                        bindingResult);

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleValidation(exception);

        assertEquals(400, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Name is required", response.getBody().getMessage());
    }

    @Test
    void testHandleException() {

        HttpServletRequest request = mock(HttpServletRequest.class);

        ResponseEntity<ApiResponse<Object>> response =
                handler.handleException(
                        new RuntimeException("Internal Error"),
                        request);

        assertEquals(500, response.getStatusCode().value());
        assertFalse(response.getBody().isSuccess());
        assertEquals("Internal Error", response.getBody().getMessage());
    }

    static class Dummy {

        public void dummyMethod(String name) {
            // required for MethodParameter
        }
    }
}