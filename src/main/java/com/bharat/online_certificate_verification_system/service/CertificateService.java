package com.bharat.online_certificate_verification_system.service;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.AdminCertificateResponse;
import com.bharat.online_certificate_verification_system.dto.response.CertificateVerificationResponse;
import com.bharat.online_certificate_verification_system.dto.response.InstitutionCertificateResponse;
import com.bharat.online_certificate_verification_system.dto.response.IssueCertificateResponse;

import java.util.List;

public interface CertificateService {

    IssueCertificateResponse issueCertificate(
            String loggedInEmail,
            IssueCertificateRequest request
    );

    CertificateVerificationResponse verifyCertificate(
            String certificateId
    );

    List<InstitutionCertificateResponse> getInstitutionCertificates(
            String loggedInEmail
    );

    void revokeCertificate(
            String certificateId,
            String loggedInEmail
    );

    List<InstitutionCertificateResponse> getRecipientCertificates(
            String loggedInEmail
    );

    List<AdminCertificateResponse> getAllCertificates();

    AdminCertificateResponse getAdminCertificate(
            String certificateId
    );

    void adminRevokeCertificate(
            String certificateId
    );
    InstitutionCertificateResponse getInstitutionCertificate(
            String certificateId,
            String loggedInEmail
    );
}