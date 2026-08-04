package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.IssueCertificateResponse;
import com.bharat.online_certificate_verification_system.entity.Certificate;
import com.bharat.online_certificate_verification_system.entity.Institution;
import com.bharat.online_certificate_verification_system.entity.User;
import com.bharat.online_certificate_verification_system.enums.CertificateStatus;
import com.bharat.online_certificate_verification_system.exception.ResourceNotFoundException;
import com.bharat.online_certificate_verification_system.repositories.CertificateRepository;
import com.bharat.online_certificate_verification_system.repositories.InstitutionRepository;
import com.bharat.online_certificate_verification_system.repositories.UserRepository;
import com.bharat.online_certificate_verification_system.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional

public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;

    @Override
    public IssueCertificateResponse issueCertificate(
            String loggedInEmail,
            IssueCertificateRequest request){

        User user = userRepository.findByEmail(loggedInEmail).orElseThrow(() ->
        new ResourceNotFoundException("User not found"));

        Institution institution = institutionRepository.findByUser(user)
                .orElseThrow(()->
                        new ResourceNotFoundException("Institution not found"));

        String certificateId = UUID.randomUUID().toString();

        Certificate certificate = Certificate.builder()
                .certificateId(certificateId)
                .recipientName(request.getRecipientName())
                .recipientEmail(request.getRecipientEmail())
                .courseName(request.getCourseName())
                .certificateTitle(request.getCertificateTitle())
                .description(request.getDescription())
                .issueDate(LocalDate.now())
                .status(CertificateStatus.ACTIVE)
                .institution(institution)
                .build();

        certificateRepository.save(certificate);

        return IssueCertificateResponse.builder()
                .certificateId(certificateId)
                .recipientName(certificate.getRecipientName())
                .courseName(certificate.getCourseName())
                .message("Certificate isssued successfully.")
                .build();
    }



}
