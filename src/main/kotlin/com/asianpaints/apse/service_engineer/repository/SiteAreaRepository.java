package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SiteAreaRepository extends JpaRepository<SiteArea, Long> {
    List<SiteArea> findByInspectionSiteId(Long inspectionSiteId);

    @Query(value = "select * from site_area where id in (:areaIds)",
            nativeQuery = true)
    Set<SiteArea> findByIds(@Param("areaIds") Set<Long> areaIds);
}
