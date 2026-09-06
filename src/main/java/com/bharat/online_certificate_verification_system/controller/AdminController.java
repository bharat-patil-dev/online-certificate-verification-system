package com.bharat.online_certificate_verification_system.controller;
import com.bharat.online_certificate_verification_system.dto.response.AdminInstitutionResponse;
import com.bharat.online_certificate_verification_system.dto.response.PendingInstitutionResponse;
import com.bharat.online_certificate_verification_system.dto.response.AdminCertificateResponse;
import com.bharat.online_certificate_verification_system.service.AdminService;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CertificateService certificateService;

    @GetMapping("/institutions/pending")
    public ResponseEntity<List<PendingInstitutionResponse>>
    getPendingInstitutions() {

        return ResponseEntity.ok(
                adminService.getPendingInstitutions()
        );
    }

    @GetMapping("/institutions/{id}")
    public ResponseEntity<AdminInstitutionResponse>
    getInstitutionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                adminService.getInstitutionById(id)
        );
    }

    @PutMapping("/institutions/{id}/approve")
    public ResponseEntity<String> approveInstitution(
            @PathVariable Long id
    ) {

        adminService.approveInstitution(id);

        return ResponseEntity.ok(
                "Institution approved successfully."
        );
    }

    @PutMapping("/institutions/{id}/reject")
    public ResponseEntity<String> rejectInstitution(
            @PathVariable Long id
    ) {

        adminService.rejectInstitution(id);

        return ResponseEntity.ok(
                "Institution rejected successfully."
        );
    }
    @GetMapping("/institutions")
    public ResponseEntity<List<AdminInstitutionResponse>>
    getAllInstitutions() {

        return ResponseEntity.ok(
                adminService.getAllInstitutions()
        );
    }
    @GetMapping("/certificates")
    public ResponseEntity<List<AdminCertificateResponse>>
    getAllCertificates() {

        return ResponseEntity.ok(
                certificateService.getAllCertificates()
        );
    }

    @GetMapping("/certificates/{certificateId}")
    public ResponseEntity<AdminCertificateResponse>
    getCertificate(
            @PathVariable String certificateId
    ) {

        return ResponseEntity.ok(
                certificateService.getAdminCertificate(certificateId)
        );
    }

    @PutMapping("/certificates/{certificateId}/revoke")
    public ResponseEntity<String>
    revokeCertificate(
            @PathVariable String certificateId
    ) {

        certificateService.adminRevokeCertificate(
                certificateId
        );

        return ResponseEntity.ok(
                "Certificate revoked successfully."
        );
    }
}