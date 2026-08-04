package com.bharat.online_certificate_verification_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueCertificateRequest {
    @NotBlank
    private String recipientName;

    @Email
    @NotBlank
    private String recipientEmail;

    @NotBlank
    private String courseName;

    @NotBlank
    private String certificateTitle;

    private String description;
}
