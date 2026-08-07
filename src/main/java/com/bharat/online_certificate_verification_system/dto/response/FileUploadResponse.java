package com.bharat.online_certificate_verification_system.dto.response;
import com.bharat.online_certificate_verification_system.repositories.InstitutionRepository;
import com.bharat.online_certificate_verification_system.repositories.UserRepository;
import com.bharat.online_certificate_verification_system.service.storage.FileStorageService;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
public class FileUploadResponse {
    private String fileName;
    private String fileUrl;
}

