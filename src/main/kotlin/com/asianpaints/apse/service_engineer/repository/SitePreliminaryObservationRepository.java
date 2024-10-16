package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.SitePreliminaryObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SitePreliminaryObservationRepository extends JpaRepository<SitePreliminaryObservation, Long> {
    Optional<SitePreliminaryObservation> findByInspectionSiteId(Long inspectionSiteId);
}
