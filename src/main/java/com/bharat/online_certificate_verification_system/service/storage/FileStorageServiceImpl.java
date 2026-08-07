package com.bharat.online_certificate_verification_system.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file, String folder) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty.");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/png")
                        || contentType.equals("image/jpeg"))) {

            throw new RuntimeException("Only PNG and JPG images are allowed.");
        }
        try {

            // Create folder if it doesn't exist
            Path folderPath = Paths.get(uploadDir, folder);
            Files.createDirectories(folderPath);

            // Get original filename
            String originalFileName =
                    StringUtils.cleanPath(file.getOriginalFilename());

            // Extract extension
            String extension = "";

            int index = originalFileName.lastIndexOf(".");

            if (index > 0) {
                extension = originalFileName.substring(index);
            }

            // Generate unique filename
            String fileName = UUID.randomUUID() + extension;

            // Destination
            Path targetLocation = folderPath.resolve(fileName);

            // Save file
            Files.copy(
                    file.getInputStream(),
                    targetLocation,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetLocation.toString();

        } catch (IOException e) {
            throw new RuntimeException("Unable to store file", e);
        }
    }
}