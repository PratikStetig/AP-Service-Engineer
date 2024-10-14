package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteAreaRepository extends JpaRepository<SiteArea, Long> {
    List<SiteArea> findByInspectionSiteId(Long inspectionSiteId);
}
