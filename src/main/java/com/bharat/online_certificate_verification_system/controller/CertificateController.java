package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.IssueCertificateResponse;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/institution")
    public ResponseEntity<IssueCertificateResponse> issueCertificate(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody IssueCertificateRequest request) {

        IssueCertificateResponse response =
                certificateService.issueCertificate(
                        userDetails.getUsername(),
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/institution/certificates/{certificateId}/revoke")
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
