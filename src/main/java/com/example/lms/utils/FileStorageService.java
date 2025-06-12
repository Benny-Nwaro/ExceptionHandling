package com.example.lms.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file, UUID userId) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        String fileName = "user_" + userId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return "/uploads/" + fileName;
    }

    public String saveCourseFile(MultipartFile file, UUID courseId) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        String fileName = "course_" + courseId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        return "/uploads/" + fileName;
    }
}

