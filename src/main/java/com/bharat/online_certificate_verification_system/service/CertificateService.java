package com.bharat.online_certificate_verification_system.service;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.IssueCertificateResponse;

public interface CertificateService {
    IssueCertificateResponse issueCertificate(
            String loggedInEmail,
            IssueCertificateRequest request);

}
