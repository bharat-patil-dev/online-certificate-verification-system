package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.response.PendingInstitutionResponse;
import com.bharat.online_certificate_verification_system.entity.Institution;
import com.bharat.online_certificate_verification_system.entity.User;
import com.bharat.online_certificate_verification_system.enums.InstitutionStatus;
import com.bharat.online_certificate_verification_system.exception.ResourceNotFoundException;
import com.bharat.online_certificate_verification_system.repositories.InstitutionRepository;
import com.bharat.online_certificate_verification_system.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final InstitutionRepository institutionRepository;

    @Override
    public List<PendingInstitutionResponse> getPendingInstitutions(){
        List<Institution> institutions =
                institutionRepository.findByStatus(InstitutionStatus.PENDING);

        return institutions.stream()
                .map(institution -> PendingInstitutionResponse.builder()
                .institutionId(institution.getId())
                .institutionName(institution.getInstitutionName())
                        .fullname(institution.getUser().getFullName())
                        .email(institution.getUser().getEmail())
                        .phone(institution.getPhone())
                        .website(institution.getWebsite())
                        .address(institution.getAddress())
                        .build())
                        .toList();

    }

    @Override
    @Transactional
    public void approveInstitution(Long institutionId) {

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Institution not found"));

        institution.setStatus(InstitutionStatus.APPROVED);

        User user = institution.getUser();
        user.setEnabled(true);

        institutionRepository.save(institution);
    }
    @Override
    @Transactional
    public void rejectInstitution(Long institutionId) {

        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Institution not found"));

        institution.setStatus(InstitutionStatus.REJECTED);

        institutionRepository.save(institution);
    }
}
