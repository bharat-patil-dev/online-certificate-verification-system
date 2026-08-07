package com.bharat.online_certificate_verification_system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CertificateVerificationResponse {
    private String certificateId;

    private String recipientName;

    private String recipientEmail;

    private String courseName;

    private String certificateTitle;

    private LocalDate issueDate;

    private String institutionName;

    private String status;
}
