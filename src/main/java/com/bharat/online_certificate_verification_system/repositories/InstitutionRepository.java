package com.bharat.online_certificate_verification_system.repositories;

import com.bharat.online_certificate_verification_system.entity.Institution;
import com.bharat.online_certificate_verification_system.entity.User;
import com.bharat.online_certificate_verification_system.enums.InstitutionStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InstitutionRepository extends CrudRepository<Institution, Long> {
    List<Institution> findByStatus(InstitutionStatus status);

    Optional<Institution> findByUser(User user);
    Optional<Institution> findByUserId(Long userId);
}
