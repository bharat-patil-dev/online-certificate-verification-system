package com.bharat.online_certificate_verification_system.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueCertificateResponse {
    private String certificateId;

    private String recipientName;

    private String courseName;

    private String message;
}
