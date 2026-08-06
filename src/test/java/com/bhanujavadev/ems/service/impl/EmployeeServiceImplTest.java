package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.dto.request.EmployeeRequest;
import com.bhanujavadev.ems.dto.request.EmployeeSearchRequest;
import com.bhanujavadev.ems.dto.response.EmployeeResponse;
import com.bhanujavadev.ems.entity.Department;
import com.bhanujavadev.ems.entity.Designation;
import com.bhanujavadev.ems.entity.Employee;
import com.bhanujavadev.ems.mapper.EmployeeMapper;
import com.bhanujavadev.ems.repository.DepartmentRepository;
import com.bhanujavadev.ems.repository.DesignationRepository;
import com.bhanujavadev.ems.repository.EmployeeRepository;
import com.bhanujavadev.ems.service.FileStorageService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DesignationRepository designationRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private FileStorageService fileStorageService;

    // ===== Test Objects =====

    private Employee employee;
    private EmployeeRequest employeeRequest;
    private EmployeeResponse employeeResponse;
    private Department department;
    private Designation designation;

    @BeforeEach
    void setUp() {

        department = Department.builder()
                .id(1L)
                .departmentName("IT")
                .build();

        designation = Designation.builder()
                .id(1L)
                .designationName("Developer")
                .build();

        employee = Employee.builder()
                .id(1L)
                .employeeCode("EMP001")
                .firstName("Bhanu")
                .lastName("Priya")
                .email("bhanu@gmail.com")
                .mobileNumber("9876543210")
                .department(department)
                .designation(designation)
                .photo("photo.jpg")
                .resume("resume.pdf")
                .build();

        employeeRequest = EmployeeRequest.builder()
                .employeeCode("EMP001")
                .firstName("Bhanu")
                .lastName("Priya")
                .email("bhanu@gmail.com")
                .mobileNumber("9876543210")
                .departmentId(1L)
                .designationId(1L)
                .build();

        employeeResponse = EmployeeResponse.builder()
                .id(1L)
                .employeeCode("EMP001")
                .firstName("Bhanu")
                .lastName("Priya")
                .email("bhanu@gmail.com")
                .departmentName("IT")
                .designationName("Developer")
                .build();
    }

    @Test
    void testCreateEmployee() {

        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setDepartmentId(1L);
        request.setDesignationId(1L);

        Department department = new Department();
        department.setId(1L);

        Designation designation = new Designation();
        designation.setId(1L);

        Employee employee = new Employee();

        EmployeeResponse response = new EmployeeResponse();

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(designationRepository.findById(1L))
                .thenReturn(Optional.of(designation));

        when(employeeMapper.toEntity(request))
                .thenReturn(employee);

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.createEmployee(request);

        // Assert
        assertEquals(response, result);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetEmployeeById() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.getEmployeeById(1L);

        // Assert
        assertEquals(1L, result.getId());

        verify(employeeRepository).findById(1L);
        verify(employeeMapper).toResponse(employee);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeNotFound() {

        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.getEmployeeById(1L)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(employeeRepository).findById(1L);
    }

    @Test
    void testDeleteEmployee() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).delete(employee);
    }

    @Test
    void testDeleteEmployee_WhenEmployeeNotFound() {

        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.deleteEmployee(1L)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    @Test
    void testUpdateEmployee() {

        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setDepartmentId(1L);
        request.setDesignationId(1L);

        Employee employee = new Employee();
        employee.setId(1L);

        Department department = new Department();
        department.setId(1L);

        Designation designation = new Designation();
        designation.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(designationRepository.findById(1L))
                .thenReturn(Optional.of(designation));

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.updateEmployee(1L, request);

        // Assert
        assertEquals(1L, result.getId());

        verify(employeeRepository).findById(1L);
        verify(departmentRepository).findById(1L);
        verify(designationRepository).findById(1L);

        verify(employeeMapper).updateEntity(employee, request);

        verify(employeeRepository).save(employee);

        verify(employeeMapper).toResponse(employee);
    }

    @Test
    void testUpdateEmployee_WhenEmployeeNotFound() {

        // Arrange
        EmployeeRequest request = new EmployeeRequest();
        request.setDepartmentId(1L);
        request.setDesignationId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.updateEmployee(1L, request)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(employeeRepository).findById(1L);

        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUploadPhoto() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);

        MultipartFile file = mock(MultipartFile.class);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(fileStorageService.uploadPhoto(file))
                .thenReturn("photos/profile.jpg");

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.uploadPhoto(1L, file);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("photos/profile.jpg", employee.getPhoto());

        verify(fileStorageService).uploadPhoto(file);
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUploadPhoto_WhenEmployeeNotFound() {

        MultipartFile file = mock(MultipartFile.class);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.uploadPhoto(1L, file)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(fileStorageService, never()).uploadPhoto(any());
    }

    @Test
    void testUploadResume() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);

        MultipartFile file = mock(MultipartFile.class);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(fileStorageService.uploadResume(file))
                .thenReturn("resumes/resume.pdf");

        when(employeeRepository.save(employee))
                .thenReturn(employee);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        EmployeeResponse result = employeeService.uploadResume(1L, file);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("resumes/resume.pdf", employee.getResume());

        verify(fileStorageService).uploadResume(file);
        verify(employeeRepository).save(employee);
    }

    @Test
    void testUploadResume_WhenEmployeeNotFound() {

        MultipartFile file = mock(MultipartFile.class);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.uploadResume(1L, file)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(fileStorageService, never()).uploadResume(any());
    }

    @Test
    void testDownloadPhoto() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setPhoto("photos/profile.jpg");

        Resource resource = mock(Resource.class);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(fileStorageService.downloadFile("photos/profile.jpg"))
                .thenReturn(resource);

        // Act
        Resource result = employeeService.downloadPhoto(1L);

        // Assert
        assertNotNull(result);
        assertEquals(resource, result);

        verify(employeeRepository).findById(1L);
        verify(fileStorageService).downloadFile("photos/profile.jpg");
    }

    @Test
    void testDownloadPhoto_WhenEmployeeNotFound() {

        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.downloadPhoto(1L)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(employeeRepository).findById(1L);
        verify(fileStorageService, never()).downloadFile(any());
    }

    @Test
    void testDownloadPhoto_WhenPhotoNotFound() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setPhoto(null);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.downloadPhoto(1L)
        );

        assertEquals("Photo not found.", exception.getMessage());

        verify(employeeRepository).findById(1L);
        verify(fileStorageService, never()).downloadFile(any());
    }

    @Test
    void testGetAllEmployees() {

        // Arrange
        EmployeeSearchRequest searchRequest = new EmployeeSearchRequest();

        Employee employee = new Employee();
        employee.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        Page<Employee> employeePage =
                new PageImpl<>(List.of(employee));

        when(employeeRepository.findAll(
                any(Specification.class),
                any(Pageable.class)))
                .thenReturn(employeePage);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        Page<EmployeeResponse> result =
                employeeService.getAllEmployees(
                        searchRequest,
                        0,
                        10,
                        "id",
                        "asc");

        // Assert
        assertEquals(1, result.getTotalElements());

        verify(employeeRepository)
                .findAll(any(Specification.class),
                        any(Pageable.class));
    }

    @Test
    void testSearchEmployees() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);

        EmployeeResponse response = new EmployeeResponse();
        response.setId(1L);

        Page<Employee> employeePage =
                new PageImpl<>(List.of(employee));

        when(employeeRepository
                .findByEmployeeCodeContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNumberContainingIgnoreCase(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(Pageable.class)
                ))
                .thenReturn(employeePage);

        when(employeeMapper.toResponse(employee))
                .thenReturn(response);

        // Act
        Page<EmployeeResponse> result =
                employeeService.searchEmployees(
                        "Bhanu",
                        0,
                        10,
                        "id",
                        "asc");

        // Assert
        assertEquals(1, result.getTotalElements());

        verify(employeeRepository)
                .findByEmployeeCodeContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrMobileNumberContainingIgnoreCase(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(Pageable.class)
                );
    }

    @Test
    void testDownloadResume() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setResume("resumes/resume.pdf");

        Resource resource = mock(Resource.class);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(fileStorageService.downloadFile("resumes/resume.pdf"))
                .thenReturn(resource);

        // Act
        Resource result = employeeService.downloadResume(1L);

        // Assert
        assertNotNull(result);
        assertEquals(resource, result);

        verify(employeeRepository).findById(1L);
        verify(fileStorageService).downloadFile("resumes/resume.pdf");
    }

    @Test
    void testDownloadResume_WhenEmployeeNotFound() {

        // Arrange
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.downloadResume(1L)
        );

        assertEquals("Employee not found", exception.getMessage());

        verify(employeeRepository).findById(1L);
        verify(fileStorageService, never()).downloadFile(any());
    }

    @Test
    void testDownloadResume_WhenResumeNotFound() {

        // Arrange
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setResume(null);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> employeeService.downloadResume(1L)
        );

        assertEquals("Resume not found.", exception.getMessage());

        verify(employeeRepository).findById(1L);
        verify(fileStorageService, never()).downloadFile(any());
    }

    @Test
    void testCreateEmployee_DepartmentNotFound() {

        when(departmentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.createEmployee(employeeRequest)
        );

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testCreateEmployee_DesignationNotFound() {

        when(departmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(department));

        when(designationRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.createEmployee(employeeRequest)
        );

        verify(employeeRepository, never()).save(any());
    }
    @Test
    void testUpdateEmployee_EmployeeNotFound() {

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.updateEmployee(1L, employeeRequest)
        );
    }
    @Test
    void testUpdateEmployee_DepartmentNotFound() {

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.updateEmployee(1L, employeeRequest)
        );
    }
    @Test
    void testUpdateEmployee_DesignationNotFound() {

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.of(employee));

        when(departmentRepository.findById(anyLong()))
                .thenReturn(Optional.of(department));

        when(designationRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> employeeService.updateEmployee(1L, employeeRequest)
        );
    }
    @Test
    void testDownloadPhoto_NotFound() {

        employee.setPhoto(null);

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.of(employee));

        assertThrows(
                RuntimeException.class,
                () -> employeeService.downloadPhoto(1L)
        );
    }
    @Test
    void testDownloadPhoto_Blank() {

        employee.setPhoto("");

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.of(employee));

        assertThrows(
                RuntimeException.class,
                () -> employeeService.downloadPhoto(1L)
        );
    }
    @Test
    void testDownloadResume_NotFound() {

        employee.setResume(null);

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.of(employee));

        assertThrows(
                RuntimeException.class,
                () -> employeeService.downloadResume(1L)
        );
    }
    @Test
    void testDownloadResume_Blank() {

        employee.setResume("");

        when(employeeRepository.findById(anyLong()))
                .thenReturn(Optional.of(employee));

        assertThrows(
                RuntimeException.class,
                () -> employeeService.downloadResume(1L)
        );
    }
    @Test
    void testGetAllEmployees_Descending() {

        Page<Employee> employeePage =
                new PageImpl<>(List.of(employee));

        when(employeeRepository.findAll(
                ArgumentMatchers.<Specification<Employee>>any(),
                ArgumentMatchers.any(Pageable.class)))
                .thenReturn(employeePage);

        when(employeeMapper.toResponse(any(Employee.class)))
                .thenReturn(employeeResponse);

        Page<EmployeeResponse> result =
                employeeService.getAllEmployees(
                        new EmployeeSearchRequest(),
                        0,
                        10,
                        "firstName",
                        "DESC"
                );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());

        verify(employeeRepository).findAll(
                ArgumentMatchers.<Specification<Employee>>any(),
                ArgumentMatchers.any(Pageable.class));

        verify(employeeMapper).toResponse(any(Employee.class));
    }

}