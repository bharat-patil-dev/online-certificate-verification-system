package com.bharat.online_certificate_verification_system.dto.response;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class PendingInstitutionResponse {
    private Long institutionId;
    private String institutionName;
    private String fullname;
    private String email;
    private String phone;
    private String website;

    private String address;
}
