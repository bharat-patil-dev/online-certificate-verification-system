package com.bharat.online_certificate_verification_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionCertificateResponse {

    private String certificateId;

    private String recipientName;

    private String institutionName;

    private String recipientEmail;

    private String courseName;

    private String certificateTitle;

    private LocalDate issueDate;

    private String status;

    private String pdfUrl;

    private String qrCodeUrl;
}