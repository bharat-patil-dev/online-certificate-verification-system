package com.bharat.online_certificate_verification_system.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;

    private String type;

    private String role;

    private String fullName;

    private String email;
}
