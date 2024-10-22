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

    @Query(value = "SELECT sa.id as siteAreaId, sa.area, sa.coating_condition as coatingCondition, " +
            "sa.corrosion_type as corrosionType, sa.inspection_site_id as inspectionSiteId, sa.rating, " +
            "sai.id as imageId, sai.image_url as imageUrl, sai.site_area_id as areaImageSiteId, " +
            "sai.uploaded_at as uploadedAt " +
            "FROM site_area sa " +
            "JOIN site_area_image sai ON sai.site_area_id = sa.id " +
            "WHERE sa.id = :inspectionSiteId", nativeQuery = true)
    List<Object[]> findSiteAreaWithImages(@Param("inspectionSiteId") Long inspectionSiteId);
}
