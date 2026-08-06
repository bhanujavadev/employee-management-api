package com.bhanujavadev.ems.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceImplTest {

    private FileStorageServiceImpl fileStorageService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {

        fileStorageService = new FileStorageServiceImpl();

        ReflectionTestUtils.setField(
                fileStorageService,
                "uploadDir",
                tempDir.toString()
        );
    }

    @Test
    void testUploadPhoto() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "photo.jpg",
                        "image/jpeg",
                        "dummy image".getBytes()
                );

        String path = fileStorageService.uploadPhoto(file);

        assertNotNull(path);
        assertTrue(path.startsWith("photos/"));

        Path stored =
                tempDir.resolve(path);

        assertTrue(Files.exists(stored));
    }

    @Test
    void testUploadResume() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "resume.pdf",
                        "application/pdf",
                        "dummy pdf".getBytes()
                );

        String path = fileStorageService.uploadResume(file);

        assertNotNull(path);
        assertTrue(path.startsWith("resumes/"));

        Path stored =
                tempDir.resolve(path);

        assertTrue(Files.exists(stored));
    }

    @Test
    void testDownloadFile() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "resume.pdf",
                        "application/pdf",
                        "dummy pdf".getBytes()
                );

        String path =
                fileStorageService.uploadResume(file);

        Resource resource =
                fileStorageService.downloadFile(path);

        assertNotNull(resource);
        assertTrue(resource.exists());
        assertTrue(resource.isReadable());
    }

    @Test
    void testDownloadFile_WhenFileNotFound() {

        RuntimeException ex =
                assertThrows(
                        RuntimeException.class,
                        () -> fileStorageService.downloadFile("photos/notfound.jpg")
                );

        assertEquals("File not found.", ex.getMessage());
    }

    @Test
    void testDeleteFile() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "photo.jpg",
                        "image/jpeg",
                        "dummy".getBytes()
                );

        String path =
                fileStorageService.uploadPhoto(file);

        Path stored =
                tempDir.resolve(path);

        assertTrue(Files.exists(stored));

        fileStorageService.deleteFile(path);

        assertFalse(Files.exists(stored));
    }

    @Test
    void testDeleteFile_WhenFileDoesNotExist() {

        assertDoesNotThrow(() ->
                fileStorageService.deleteFile("photos/unknown.jpg"));
    }

    @Test
    void testUploadPhoto_WithEmptyFile() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "photo.jpg",
                        "image/jpeg",
                        new byte[0]
                );

        String path =
                fileStorageService.uploadPhoto(file);

        assertNotNull(path);
        assertTrue(path.startsWith("photos/"));
    }

    @Test
    void testUploadResume_WithNoExtension() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "resume",
                        "application/pdf",
                        "dummy".getBytes()
                );

        String path =
                fileStorageService.uploadResume(file);

        assertNotNull(path);
        assertTrue(path.startsWith("resumes/"));
    }

    @Test
    void testDownloadUploadedPhoto() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "image.png",
                        "image/png",
                        "image".getBytes()
                );

        String path =
                fileStorageService.uploadPhoto(file);

        Resource resource =
                fileStorageService.downloadFile(path);

        assertTrue(resource.exists());
    }

    @Test
    void testDeleteUploadedResume() throws IOException {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "resume.pdf",
                        "application/pdf",
                        "resume".getBytes()
                );

        String path =
                fileStorageService.uploadResume(file);

        Path stored =
                tempDir.resolve(path);

        assertTrue(Files.exists(stored));

        fileStorageService.deleteFile(path);

        assertFalse(Files.exists(stored));
    }
}