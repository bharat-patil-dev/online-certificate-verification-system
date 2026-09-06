package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.response.FileUploadResponse;
import com.bharat.online_certificate_verification_system.dto.response.InstitutionProfileResponse;
import com.bharat.online_certificate_verification_system.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/institution")
@RequiredArgsConstructor
public class InstitutionController {

    private final InstitutionService institutionService;


    @GetMapping("/profile")
    public ResponseEntity<InstitutionProfileResponse>
    getProfile(
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        InstitutionProfileResponse response =
                institutionService.getProfile(email);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/logo")
    public ResponseEntity<FileUploadResponse>
    uploadLogo(
            @RequestParam("logo") MultipartFile logo,
            Authentication authentication
    ) {

        String email =
                authentication.getName();

        FileUploadResponse response =
                institutionService.uploadLogo(
                        email,
                        logo
                );

        return ResponseEntity.ok(response);
    }
}