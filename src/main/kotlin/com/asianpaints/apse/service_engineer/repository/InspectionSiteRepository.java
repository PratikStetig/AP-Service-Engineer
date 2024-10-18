package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InspectionSiteRepository extends JpaRepository<InspectionSite, Long> {

    @Query(value = "SELECT * FROM INSPECTION_SITE WHERE CONDUCTED_BY = :conductedBy", nativeQuery = true)
    List<InspectionSite> findAllInspectionSiteConductedBy(Long conductedBy);
}
