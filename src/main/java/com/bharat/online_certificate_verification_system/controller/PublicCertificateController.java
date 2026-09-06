package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.response.CertificateVerificationResponse;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/certificates")
@RequiredArgsConstructor
public class PublicCertificateController {

    private final CertificateService certificateService;

    @GetMapping("/verify/{certificateId}")
    public ResponseEntity<CertificateVerificationResponse> verifyCertificate(
            @PathVariable String certificateId) {

        CertificateVerificationResponse response =
                certificateService.verifyCertificate(certificateId);

        return ResponseEntity.ok(response);
    }
}