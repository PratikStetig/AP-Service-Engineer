package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.UserDesignation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDesignationRepository extends JpaRepository<UserDesignation, Long> {
    Optional<UserDesignation> findByDesignation(String designation);
}
