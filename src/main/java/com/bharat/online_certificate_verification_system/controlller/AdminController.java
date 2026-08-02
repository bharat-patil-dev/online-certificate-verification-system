package com.bharat.online_certificate_verification_system.controlller;

import com.bharat.online_certificate_verification_system.dto.response.PendingInstitutionResponse;
import com.bharat.online_certificate_verification_system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/institutions/pending")
    public ResponseEntity<List<PendingInstitutionResponse>> getPendingInstitutins(){
        return ResponseEntity.ok(adminService.getPendingInstitutions());
    }

    @PutMapping("/institutions/{id}/approve")
    public ResponseEntity<String> approveInstitution(@PathVariable Long id) {

        adminService.approveInstitution(id);

        return ResponseEntity.ok("Institution approved successfully.");
    }
    @PutMapping("/institutions/{id}/reject")
    public ResponseEntity<String> rejectInstitution(@PathVariable Long id) {

        adminService.rejectInstitution(id);

        return ResponseEntity.ok("Institution rejected successfully.");
    }

}
