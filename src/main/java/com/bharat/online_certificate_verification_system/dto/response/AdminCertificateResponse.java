package com.bharat.online_certificate_verification_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AdminCertificateResponse {

    private String certificateId;

    private String recipientName;

    private String recipientEmail;

    private String institutionName;

    private String courseName;

    private String certificateTitle;

    private LocalDate issueDate;

    private String status;

    private String pdfUrl;

    private String qrCodeUrl;
}