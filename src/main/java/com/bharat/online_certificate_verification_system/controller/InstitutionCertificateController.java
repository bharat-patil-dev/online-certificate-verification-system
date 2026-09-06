package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.InstitutionCertificateResponse;
import com.bharat.online_certificate_verification_system.dto.response.IssueCertificateResponse;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/institution/certificates")
@RequiredArgsConstructor
public class InstitutionCertificateController {

    private final CertificateService certificateService;


    // =========================================================
    // ISSUE CERTIFICATE
    // =========================================================

    @PostMapping("/issue")
    public ResponseEntity<IssueCertificateResponse> issueCertificate(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody IssueCertificateRequest request
    ) {

        IssueCertificateResponse response =
                certificateService.issueCertificate(
                        userDetails.getUsername(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================================
    // GET ALL CERTIFICATES OF LOGGED-IN INSTITUTION
    // =========================================================

    @GetMapping
    public ResponseEntity<List<InstitutionCertificateResponse>>
    getCertificates(
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        List<InstitutionCertificateResponse> certificates =
                certificateService.getInstitutionCertificates(
                        userDetails.getUsername()
                );

        return ResponseEntity.ok(certificates);
    }


    // =========================================================
    // GET ONE CERTIFICATE
    // =========================================================

    @GetMapping("/{certificateId}")
    public ResponseEntity<InstitutionCertificateResponse>
    getCertificate(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String certificateId
    ) {

        InstitutionCertificateResponse response =
                certificateService.getInstitutionCertificate(
                        certificateId,
                        userDetails.getUsername()
                );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // REVOKE CERTIFICATE
    // =========================================================

    @PutMapping("/{certificateId}/revoke")
    public ResponseEntity<String> revokeCertificate(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String certificateId) {

        certificateService.revokeCertificate(
                certificateId,
                userDetails.getUsername()
        );

        return ResponseEntity.ok(
                "Certificate revoked successfully."
        );
    }
}