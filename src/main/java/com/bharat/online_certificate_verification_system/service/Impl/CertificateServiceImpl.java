package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.CertificateVerificationResponse;
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
import com.bharat.online_certificate_verification_system.service.pdf.PdfService;
import com.bharat.online_certificate_verification_system.service.qr.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final QrCodeService qrCodeService;
    private final PdfService pdfService;

    @Override
    public IssueCertificateResponse issueCertificate(
            String loggedInEmail,
            IssueCertificateRequest request) {

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Institution institution = institutionRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Institution not found"));

        // Create certificate without certificateId
        Certificate certificate = Certificate.builder()
                .recipientName(request.getRecipientName())
                .recipientEmail(request.getRecipientEmail())
                .courseName(request.getCourseName())
                .certificateTitle(request.getCertificateTitle())
                .description(request.getDescription())
                .issueDate(LocalDate.now())
                .status(CertificateStatus.ACTIVE)
                .institution(institution)
                .build();

        // First save (database generates ID)
        certificateRepository.save(certificate);

        // Generate readable certificate ID
        String certificateId = generateCertificateId(certificate.getId());
        certificate.setCertificateId(certificateId);

        // Generate QR Code
        String qrCodePath = qrCodeService.generateQrCode(certificateId);
        certificate.setQrCodeUrl(qrCodePath);

        String pdfPath =
                pdfService.generateCertificatePdf(certificate.getId());

        certificate.setPdfUrl(pdfPath);

        // Save updated certificate
        certificateRepository.save(certificate);

        return IssueCertificateResponse.builder()
                .certificateId(certificate.getCertificateId())
                .recipientName(certificate.getRecipientName())
                .courseName(certificate.getCourseName())
                .message("Certificate issued successfully.")
                .build();
    }
    private String generateCertificateId(Long id) {

        return String.format(
                "OCVS-%d-%06d",
                LocalDate.now().getYear(),
                id
        );
    }

    @Override
    public CertificateVerificationResponse verifyCertificate(String certificateId) {
        return null;
    }


}