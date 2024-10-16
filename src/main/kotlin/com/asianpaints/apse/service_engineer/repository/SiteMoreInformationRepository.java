package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.SiteMoreInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteMoreInformationRepository extends JpaRepository<SiteMoreInformation, Long> {
    Optional<SiteMoreInformation> findBySiteCorrosivityEnvironment_Id(Long corrosivityEnvironmentId);
}
