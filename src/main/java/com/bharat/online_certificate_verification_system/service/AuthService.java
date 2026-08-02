package com.bharat.online_certificate_verification_system.service;

import com.bharat.online_certificate_verification_system.dto.InstitutionRegistrationRequest;
import com.bharat.online_certificate_verification_system.dto.auth.LoginRequest;
import com.bharat.online_certificate_verification_system.dto.auth.LoginResponse;

public interface AuthService {
    void registerInstitution(InstitutionRegistrationRequest request);

    LoginResponse login(LoginRequest request);
}
