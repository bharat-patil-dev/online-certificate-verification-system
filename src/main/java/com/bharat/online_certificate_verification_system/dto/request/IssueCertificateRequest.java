package com.bharat.online_certificate_verification_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueCertificateRequest {

    @NotBlank(
            message = "Recipient name is required"
    )
    private String recipientName;


    @NotBlank(
            message = "Recipient email is required"
    )
    @Email(
            message = "Enter a valid recipient email"
    )
    private String recipientEmail;


    @NotBlank(
            message = "Course name is required"
    )
    private String courseName;


    @NotBlank(
            message = "Certificate title is required"
    )
    private String certificateTitle;


    private String description;
}