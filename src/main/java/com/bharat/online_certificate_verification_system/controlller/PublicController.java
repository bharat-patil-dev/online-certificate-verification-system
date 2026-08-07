package com.bharat.online_certificate_verification_system.controlller;

import com.bharat.online_certificate_verification_system.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {
    private final CertificateService  certificateService;

    public ResponseEntity<?> verifyCertificate(@PathVariable String certificateId){
        return ResponseEntity.ok(certificateService.verifyCertificate(certificateId)
        );
    }
}
