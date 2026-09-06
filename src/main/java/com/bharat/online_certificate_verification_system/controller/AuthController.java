package com.bharat.online_certificate_verification_system.controller;

import com.bharat.online_certificate_verification_system.dto.InstitutionRegistrationRequest;
import com.bharat.online_certificate_verification_system.dto.RecipientRegistrationRequest;
import com.bharat.online_certificate_verification_system.dto.auth.LoginRequest;
import com.bharat.online_certificate_verification_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/institution")
    public ResponseEntity<?> registerInstitution(
            @Valid @RequestBody InstitutionRegistrationRequest request) {

        authService.registerInstitution(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        "Institution registered successfully. waiting for admins approval."
                );
    }

    @PostMapping("/register/recipient")
    public ResponseEntity<?> registerRecipient(
            @Valid @RequestBody RecipientRegistrationRequest request) {

        authService.registerRecipient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Recipient registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}