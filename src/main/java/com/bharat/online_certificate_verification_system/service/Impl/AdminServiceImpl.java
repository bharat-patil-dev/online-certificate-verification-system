package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.response.AdminInstitutionResponse;
import com.bharat.online_certificate_verification_system.dto.response.PendingInstitutionResponse;
import com.bharat.online_certificate_verification_system.entity.Institution;
import com.bharat.online_certificate_verification_system.enums.InstitutionStatus;
import com.bharat.online_certificate_verification_system.exception.ResourceNotFoundException;
import com.bharat.online_certificate_verification_system.repositories.InstitutionRepository;
import com.bharat.online_certificate_verification_system.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final InstitutionRepository institutionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PendingInstitutionResponse> getPendingInstitutions() {

        List<Institution> institutions =
                institutionRepository.findByStatus(
                        InstitutionStatus.PENDING
                );

        return institutions.stream()
                .map(institution ->
                        PendingInstitutionResponse.builder()
                                .institutionId(institution.getId())
                                .institutionName(
                                        institution.getInstitutionName()
                                )
                                .fullName(
                                        institution.getUser().getFullName()
                                )
                                .email(
                                        institution.getUser().getEmail()
                                )
                                .phone(
                                        institution.getPhone()
                                )
                                .website(
                                        institution.getWebsite()
                                )
                                .address(
                                        institution.getAddress()
                                )
                                .build()
                )
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminInstitutionResponse getInstitutionById(
            Long institutionId
    ) {

        Institution institution =
                institutionRepository.findById(institutionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"
                                )
                        );

        return mapToResponse(institution);
    }

    @Override
    public void approveInstitution(Long institutionId) {

        Institution institution =
                institutionRepository.findById(institutionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"
                                )
                        );

        institution.setStatus(
                InstitutionStatus.APPROVED
        );

        // Important:
        // approved institution must also be enabled
        institution.getUser().setEnabled(true);

        institutionRepository.save(institution);
    }

    @Override
    public void rejectInstitution(Long institutionId) {

        Institution institution =
                institutionRepository.findById(institutionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Institution not found"
                                )
                        );

        institution.setStatus(
                InstitutionStatus.REJECTED
        );

        institution.getUser().setEnabled(false);

        institutionRepository.save(institution);
    }

    private AdminInstitutionResponse mapToResponse(
            Institution institution
    ) {

        return AdminInstitutionResponse.builder()
                .id(institution.getId())
                .institutionName(
                        institution.getInstitutionName()
                )
                .phone(
                        institution.getPhone()
                )
                .website(
                        institution.getWebsite()
                )
                .address(
                        institution.getAddress()
                )
                .logoUrl(
                        institution.getLogoUrl()
                )
                .status(
                        institution.getStatus().name()
                )
                .userEmail(
                        institution.getUser().getEmail()
                )
                .build();
    }
    @Override
    @Transactional(readOnly = true)
    public List<AdminInstitutionResponse> getAllInstitutions() {

        return StreamSupport
                .stream(
                        institutionRepository.findAll().spliterator(),
                        false
                )
                .map(this::mapToResponse)
                .toList();
    }
}