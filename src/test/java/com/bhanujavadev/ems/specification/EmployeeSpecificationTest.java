package com.bhanujavadev.ems.specification;

import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.entity.Employee;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeSpecificationTest {

    @Test
    void testSearch_AllFilters() throws Exception {

        EmployeeSearchRequest request = new EmployeeSearchRequest();

        request.setKeyword("bhanu");
        request.setDepartmentId(1L);
        request.setDesignationId(2L);
        request.setActive(true);

        Root<Employee> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Path<Object> firstName = mock(Path.class);
        Path<Object> lastName = mock(Path.class);
        Path<Object> email = mock(Path.class);
        Path<Object> employeeCode = mock(Path.class);
        Path<Object> department = mock(Path.class);
        Path<Object> designation = mock(Path.class);
        Path<Object> active = mock(Path.class);
        Path<Object> deptId = mock(Path.class);
        Path<Object> desigId = mock(Path.class);

        Predicate predicate = mock(Predicate.class);

        when(root.get("firstName")).thenReturn(firstName);
        when(root.get("lastName")).thenReturn(lastName);
        when(root.get("email")).thenReturn(email);
        when(root.get("employeeCode")).thenReturn(employeeCode);
        when(root.get("department")).thenReturn(department);
        when(root.get("designation")).thenReturn(designation);
        when(root.get("active")).thenReturn(active);

        when(department.get("id")).thenReturn(deptId);
        when(designation.get("id")).thenReturn(desigId);

        when(cb.lower(any())).thenReturn(mock(Expression.class));

        when(cb.like(any(), anyString())).thenReturn(predicate);
        when(cb.or(any(Predicate.class), any(Predicate.class),
                any(Predicate.class), any(Predicate.class)))
                .thenReturn(predicate);

        when(cb.equal(any(), any())).thenReturn(predicate);

        when(cb.and(any(Predicate[].class))).thenReturn(predicate);

        Specification<Employee> specification =
                EmployeeSpecification.search(request);

        Predicate result =
                specification.toPredicate(root, query, cb);

        assertNotNull(result);
    }

    @Test
    void testSearch_NoFilters() {

        EmployeeSearchRequest request =
                new EmployeeSearchRequest();

        Root<Employee> root = mock(Root.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        CriteriaBuilder cb = mock(CriteriaBuilder.class);

        Predicate predicate = mock(Predicate.class);

        when(cb.and(any(Predicate[].class)))
                .thenReturn(predicate);

        Specification<Employee> specification =
                EmployeeSpecification.search(request);

        Predicate result =
                specification.toPredicate(root, query, cb);

        assertNotNull(result);
    }

    @Test
    void testPrivateConstructor() throws Exception {

        Constructor<EmployeeSpecification> constructor =
                EmployeeSpecification.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        EmployeeSpecification specification =
                constructor.newInstance();

        assertNotNull(specification);
    }
}