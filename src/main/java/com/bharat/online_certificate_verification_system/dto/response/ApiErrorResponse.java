package com.bharat.online_certificate_verification_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiErrorResponse {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
