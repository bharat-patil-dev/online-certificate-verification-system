package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.response.FileUploadResponse;
import com.bharat.online_certificate_verification_system.entity.Institution;
import com.bharat.online_certificate_verification_system.entity.User;
import com.bharat.online_certificate_verification_system.exception.ResourceNotFoundException;
import com.bharat.online_certificate_verification_system.repositories.InstitutionRepository;
import com.bharat.online_certificate_verification_system.repositories.UserRepository;
import com.bharat.online_certificate_verification_system.service.InstitutionService;
import com.bharat.online_certificate_verification_system.service.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final FileStorageService fileStorageService;

    @Override
    public FileUploadResponse uploadLogo(String email, MultipartFile logo) {


        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Institution institution = institutionRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Institution not found"));

        String logoPath = fileStorageService.storeFile(logo, "logos");

        institution.setLogoUrl(logoPath);

        institutionRepository.save(institution);
        System.out.println("Email: " + email);
        System.out.println("User ID: " + user.getId());
        System.out.println("User Email: " + user.getEmail());
        return FileUploadResponse.builder()
                .fileName(logo.getOriginalFilename())
                .fileUrl(logoPath)
                .build();
    }


}