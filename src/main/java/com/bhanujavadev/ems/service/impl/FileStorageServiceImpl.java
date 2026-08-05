package com.bhanujavadev.ems.service.impl;

import com.bhanujavadev.ems.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String uploadPhoto(MultipartFile file) {

        return storeFile(file, "photos");
    }

    @Override
    public String uploadResume(MultipartFile file) {

        return storeFile(file, "resumes");
    }

    @Override
    public void deleteFile(String filePath) {

        try {

            Path path = Paths.get(uploadDir).resolve(filePath);

            Files.deleteIfExists(path);

        } catch (IOException e) {

            throw new RuntimeException("Unable to delete file.");
        }
    }

    private String storeFile(MultipartFile file, String folderName) {

        try {

            Path uploadPath = Paths.get(uploadDir, folderName);

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            String originalFilename =
                    StringUtils.cleanPath(file.getOriginalFilename());

            String extension = "";

            int index = originalFilename.lastIndexOf(".");

            if (index > 0) {

                extension = originalFilename.substring(index);
            }

            String fileName =
                    UUID.randomUUID() + extension;

            Path targetLocation =
                    uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return folderName + "/" + fileName;

        } catch (IOException e) {

            throw new RuntimeException("Could not store file.");
        }
    }
    @Override
    public Resource downloadFile(String filePath) {

        try {

            Path path = Paths.get(uploadDir).resolve(filePath).normalize();

            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("File not found.");

        } catch (MalformedURLException e) {

            throw new RuntimeException("File not found.");
        }
    }
}