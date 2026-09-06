package com.bharat.online_certificate_verification_system.repositories;

import com.bharat.online_certificate_verification_system.entity.Certificate;
import com.bharat.online_certificate_verification_system.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    Optional<Certificate> findByCertificateId(String certificateId);

    List<Certificate> findByInstitution(Institution institution);

    Optional<Certificate> findByCertificateIdAndInstitution(
            String certificateId,
            Institution institution
    );
}