package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.request.IssueCertificateRequest;
import com.bharat.online_certificate_verification_system.dto.response.AdminCertificateResponse;
import com.bharat.online_certificate_verification_system.dto.response.CertificateVerificationResponse;
import com.bharat.online_certificate_verification_system.dto.response.InstitutionCertificateResponse;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final InstitutionRepository institutionRepository;
    private final UserRepository userRepository;
    private final QrCodeService qrCodeService;
    private final PdfService pdfService;


    // =========================================================
    // ISSUE CERTIFICATE
    // =========================================================

    @Override
    @Transactional
    public IssueCertificateResponse issueCertificate(
            String loggedInEmail,
            IssueCertificateRequest request) {

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Institution institution = institutionRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Institution not found for this user"));

        Certificate certificate = new Certificate();

        certificate.setRecipientName(request.getRecipientName());
        certificate.setRecipientEmail(request.getRecipientEmail());
        certificate.setCourseName(request.getCourseName());
        certificate.setCertificateTitle(request.getCertificateTitle());
        certificate.setIssueDate(LocalDate.now());
        certificate.setDescription(request.getDescription());
        certificate.setStatus(CertificateStatus.ACTIVE);
        certificate.setInstitution(institution);

        // Generate certificate ID before saving
        String certificateId =
                "OCVS-" + LocalDate.now().getYear() + "-"
                        + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        certificate.setCertificateId(certificateId);

        // Save certificate
        Certificate savedCertificate =
                certificateRepository.save(certificate);

        // Generate QR code
        qrCodeService.generateQrCode(certificateId);

        // Generate PDF
        pdfService.generateCertificatePdf(
                savedCertificate.getId()
        );

        // Set file URLs
        String qrCodeUrl =
                "http://localhost:8080/api/public/files/qr/"
                        + certificateId + ".png";

        String pdfUrl =
                "http://localhost:8080/api/public/files/pdf/"
                        + certificateId + ".pdf";

        savedCertificate.setQrCodeUrl(qrCodeUrl);
        savedCertificate.setPdfUrl(pdfUrl);

        // Save URLs
        certificateRepository.save(savedCertificate);

        // Response fields according to your IssueCertificateResponse
        return IssueCertificateResponse.builder()
                .certificateId(savedCertificate.getCertificateId())
                .recipientName(savedCertificate.getRecipientName())
                .courseName(savedCertificate.getCourseName())
                .message("Certificate issued successfully.")
                .build();
    }

    // =========================================================
    // GENERATE CERTIFICATE ID
    // =========================================================

    private String generateCertificateId(Long id) {

        return String.format(
                "OCVS-%d-%06d",
                LocalDate.now().getYear(),
                id
        );
    }


    // =========================================================
    // PUBLIC VERIFY CERTIFICATE
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public CertificateVerificationResponse verifyCertificate(
            String certificateId) {

        Certificate certificate =
                certificateRepository
                        .findByCertificateId(certificateId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certificate not found"));

        return CertificateVerificationResponse.builder()
                .certificateId(
                        certificate.getCertificateId())
                .recipientName(
                        certificate.getRecipientName())
                .recipientEmail(
                        certificate.getRecipientEmail())
                .courseName(
                        certificate.getCourseName())
                .certificateTitle(
                        certificate.getCertificateTitle())
                .issueDate(
                        certificate.getIssueDate())
                .institutionName(
                        certificate.getInstitution()
                                .getInstitutionName())
                .status(
                        certificate.getStatus().name())
                .pdfUrl(
                        certificate.getPdfUrl())
                .qrCodeUrl(
                        certificate.getQrCodeUrl())
                .build();
    }


    // =========================================================
    // GET INSTITUTION CERTIFICATES
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<InstitutionCertificateResponse>
    getInstitutionCertificates(String loggedInEmail) {

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Institution institution =
                institutionRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"));

        List<Certificate> certificates =
                certificateRepository
                        .findByInstitution(institution);

        return certificates.stream()
                .map(certificate ->
                        InstitutionCertificateResponse.builder()
                                .certificateId(
                                        certificate.getCertificateId())
                                .recipientName(
                                        certificate.getRecipientName())
                                .institutionName(
                                        certificate.getInstitution()
                                                .getInstitutionName())
                                .recipientEmail(
                                        certificate.getRecipientEmail())
                                .courseName(
                                        certificate.getCourseName())
                                .certificateTitle(
                                        certificate.getCertificateTitle())
                                .issueDate(
                                        certificate.getIssueDate())
                                .status(
                                        certificate.getStatus().name())
                                .pdfUrl(
                                        certificate.getPdfUrl())
                                .qrCodeUrl(
                                        certificate.getQrCodeUrl())
                                .build()
                )
                .toList();
    }


    // =========================================================
    // GET INSTITUTION CERTIFICATE BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public InstitutionCertificateResponse
    getInstitutionCertificate(
            String certificateId,
            String loggedInEmail) {

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Institution institution =
                institutionRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"));

        Certificate certificate =
                certificateRepository
                        .findByCertificateIdAndInstitution(
                                certificateId,
                                institution)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certificate not found"));

        return InstitutionCertificateResponse.builder()
                .certificateId(
                        certificate.getCertificateId())
                .recipientName(
                        certificate.getRecipientName())
                .institutionName(
                        certificate.getInstitution()
                                .getInstitutionName())
                .recipientEmail(
                        certificate.getRecipientEmail())
                .courseName(
                        certificate.getCourseName())
                .certificateTitle(
                        certificate.getCertificateTitle())
                .issueDate(
                        certificate.getIssueDate())
                .status(
                        certificate.getStatus().name())
                .pdfUrl(
                        certificate.getPdfUrl())
                .qrCodeUrl(
                        certificate.getQrCodeUrl())
                .build();
    }


    // =========================================================
    // REVOKE INSTITUTION CERTIFICATE
    // =========================================================

    @Override
    public void revokeCertificate(
            String certificateId,
            String loggedInEmail) {

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        Institution institution =
                institutionRepository.findByUser(user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"));

        Certificate certificate =
                certificateRepository
                        .findByCertificateIdAndInstitution(
                                certificateId,
                                institution)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certificate not found"));

        certificate.setStatus(
                CertificateStatus.REVOKED);

        certificateRepository.save(certificate);
    }


    // =========================================================
    // RECIPIENT CERTIFICATES
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<InstitutionCertificateResponse>
    getRecipientCertificates(String loggedInEmail) {

        User user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        List<Certificate> certificates =
                certificateRepository.findAll()
                        .stream()
                        .filter(certificate ->
                                certificate.getRecipientEmail() != null
                                        && certificate
                                        .getRecipientEmail()
                                        .equalsIgnoreCase(
                                                user.getEmail()))
                        .toList();

        return certificates.stream()
                .map(certificate ->
                        InstitutionCertificateResponse.builder()
                                .certificateId(
                                        certificate.getCertificateId())
                                .recipientName(
                                        certificate.getRecipientName())
                                .institutionName(
                                        certificate.getInstitution()
                                                .getInstitutionName())
                                .recipientEmail(
                                        certificate.getRecipientEmail())
                                .courseName(
                                        certificate.getCourseName())
                                .certificateTitle(
                                        certificate.getCertificateTitle())
                                .issueDate(
                                        certificate.getIssueDate())
                                .status(
                                        certificate.getStatus().name())
                                .pdfUrl(
                                        certificate.getPdfUrl())
                                .qrCodeUrl(
                                        certificate.getQrCodeUrl())
                                .build()
                )
                .toList();
    }


    // =========================================================
    // ADMIN - GET ALL CERTIFICATES
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<AdminCertificateResponse>
    getAllCertificates() {

        return certificateRepository
                .findAll()
                .stream()
                .map(this::mapToAdminResponse)
                .toList();
    }


    // =========================================================
    // ADMIN - GET CERTIFICATE
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public AdminCertificateResponse
    getAdminCertificate(String certificateId) {

        Certificate certificate =
                certificateRepository
                        .findByCertificateId(certificateId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certificate not found"));

        return mapToAdminResponse(certificate);
    }


    // =========================================================
    // ADMIN - REVOKE CERTIFICATE
    // =========================================================

    @Override
    public void adminRevokeCertificate(
            String certificateId) {

        Certificate certificate =
                certificateRepository
                        .findByCertificateId(certificateId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Certificate not found"));

        certificate.setStatus(
                CertificateStatus.REVOKED);

        certificateRepository.save(certificate);
    }


    // =========================================================
    // ADMIN CERTIFICATE RESPONSE MAPPER
    // =========================================================

    private AdminCertificateResponse mapToAdminResponse(
            Certificate certificate) {

        return AdminCertificateResponse.builder()
                .certificateId(
                        certificate.getCertificateId())
                .recipientName(
                        certificate.getRecipientName())
                .recipientEmail(
                        certificate.getRecipientEmail())
                .institutionName(
                        certificate.getInstitution()
                                .getInstitutionName())
                .courseName(
                        certificate.getCourseName())
                .certificateTitle(
                        certificate.getCertificateTitle())
                .issueDate(
                        certificate.getIssueDate())
                .status(
                        certificate.getStatus().name())
                .pdfUrl(
                        certificate.getPdfUrl())
                .qrCodeUrl(
                        certificate.getQrCodeUrl())
                .build();
    }
}