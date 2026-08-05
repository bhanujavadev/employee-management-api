package com.bhanujavadev.ems.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadPhoto(MultipartFile file);

    String uploadResume(MultipartFile file);

    void deleteFile(String filePath);

    Resource downloadFile(String filePath);
}