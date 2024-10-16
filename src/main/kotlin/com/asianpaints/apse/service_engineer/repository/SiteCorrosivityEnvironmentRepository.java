package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteCorrosivityEnvironmentRepository extends JpaRepository<SiteCorrosivityEnvironment, Long> {
    List<SiteCorrosivityEnvironment> findByInspectionSiteId(Long inspectionSiteId);
}
