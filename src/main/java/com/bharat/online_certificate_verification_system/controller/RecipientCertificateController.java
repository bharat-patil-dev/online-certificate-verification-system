package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.response.InstitutionCertificateResponse;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipient/certificates")
@RequiredArgsConstructor
public class RecipientCertificateController {

    private final CertificateService certificateService;

    @GetMapping
    public ResponseEntity<List<InstitutionCertificateResponse>> getCertificates(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<InstitutionCertificateResponse> certificates =
                certificateService.getRecipientCertificates(
                        userDetails.getUsername()
                );

        return ResponseEntity.ok(certificates);
    }
}