package com.bharat.online_certificate_verification_system.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminInstitutionResponse {

    private Long id;

    private String institutionName;

    private String phone;

    private String website;

    private String address;

    private String logoUrl;

    private String status;

    private String userEmail;
}