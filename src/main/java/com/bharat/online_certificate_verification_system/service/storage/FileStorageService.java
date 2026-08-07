package com.bharat.online_certificate_verification_system.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String folder);
}
