package com.bharat.online_certificate_verification_system.repositories;

import com.bharat.online_certificate_verification_system.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByCertificateId(String certificateId);

    boolean existsByCertificateId(String certificateId);
}
