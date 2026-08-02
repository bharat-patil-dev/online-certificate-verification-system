package com.bharat.online_certificate_verification_system.service;

import com.bharat.online_certificate_verification_system.dto.response.PendingInstitutionResponse;

import java.util.List;

public interface AdminService {
    List<PendingInstitutionResponse> getPendingInstitutions();
    void approveInstitution(Long institutionId);
    void rejectInstitution(Long institutionId);
}
