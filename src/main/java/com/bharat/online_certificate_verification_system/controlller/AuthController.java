package com.bharat.online_certificate_verification_system.controlller;

import com.bharat.online_certificate_verification_system.dto.InstitutionRegistrationRequest;
import com.bharat.online_certificate_verification_system.dto.auth.LoginRequest;
import com.bharat.online_certificate_verification_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/institution")
    public ResponseEntity<?> registerInstitution(@Valid @RequestBody InstitutionRegistrationRequest request){

        System.out.println("Controller reached!");
        authService.registerInstitution(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Institution registered successfully. waiting for admins approval.");
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(
            @Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
