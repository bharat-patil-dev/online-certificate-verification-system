package com.bharat.online_certificate_verification_system.service;

import com.bharat.online_certificate_verification_system.dto.response.FileUploadResponse;
import com.bharat.online_certificate_verification_system.dto.response.InstitutionProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface InstitutionService {

    FileUploadResponse uploadLogo(String email, MultipartFile logo);
    InstitutionProfileResponse getProfile(
            String email
    );

}
