package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.response.FileUploadResponse;
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

    @PostMapping("/logo")
    public ResponseEntity<FileUploadResponse> uploadLogo(
            @RequestParam("logo") MultipartFile logo,
            Authentication authentication) {
        System.out.println("InstitutionController reached");
        System.out.println(authentication);

        System.out.println("Authentication = " + authentication);

        String email = authentication.getName();

        System.out.println("Email = " + email);

        FileUploadResponse response =
                institutionService.uploadLogo(email, logo);

        return ResponseEntity.ok(response);
    }
}