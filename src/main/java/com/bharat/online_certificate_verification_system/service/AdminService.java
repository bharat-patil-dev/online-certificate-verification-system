package com.bharat.online_certificate_verification_system.service;

import com.bharat.online_certificate_verification_system.dto.response.AdminInstitutionResponse;
import com.bharat.online_certificate_verification_system.dto.response.PendingInstitutionResponse;

import java.util.List;

public interface AdminService {

    List<PendingInstitutionResponse> getPendingInstitutions();

    List<AdminInstitutionResponse> getAllInstitutions();

    AdminInstitutionResponse getInstitutionById(Long institutionId);

    void approveInstitution(Long institutionId);

    void rejectInstitution(Long institutionId);
}