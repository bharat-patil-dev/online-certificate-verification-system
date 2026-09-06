package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.response.FileUploadResponse;
import com.bharat.online_certificate_verification_system.dto.response.InstitutionProfileResponse;
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

        try {
            System.out.println("Step 1");

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("User not found"));

            System.out.println("Step 2");

            Institution institution = institutionRepository.findByUserId(user.getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Institution not found"));

            System.out.println("Step 3");

            String logoPath = fileStorageService.storeFile(logo, "logos");

            System.out.println("Step 4");

            institution.setLogoUrl(logoPath);

            institutionRepository.save(institution);

            System.out.println("Step 5");

            return FileUploadResponse.builder()
                    .fileName(logo.getOriginalFilename())
                    .fileUrl(logoPath)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Override
    public InstitutionProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Institution institution =
                institutionRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"
                                ));

        return InstitutionProfileResponse.builder()
                .institutionId(institution.getId())
                .institutionName(institution.getInstitutionName())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(institution.getPhone())
                .website(institution.getWebsite())
                .address(institution.getAddress())
                .status(institution.getStatus().name())
                .logoUrl(institution.getLogoUrl())
                .build();
    }


}