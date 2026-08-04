package com.bharat.online_certificate_verification_system.controlller;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.IssueCertificateResponse;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/istitution")
@RequiredArgsConstructor
public class InstitutionController {
    private final CertificateService certificateService;

    public ResponseEntity<?> issueCertificate(@Valid @RequestBody IssueCertificateRequest request,
                                              Authentication authentication){
        String email= authentication.getName();

        IssueCertificateResponse response=
                certificateService.issueCertificate(email , request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



}
