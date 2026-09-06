package com.bharat.online_certificate_verification_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionProfileResponse {

    private Long institutionId;

    private String institutionName;

    private String fullName;

    private String email;

    private String phone;

    private String website;

    private String address;

    private String status;

    private String logoUrl;
}