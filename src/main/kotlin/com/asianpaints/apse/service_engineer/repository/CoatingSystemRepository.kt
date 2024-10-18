package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CoatingSystemRepository : JpaRepository<CoatingSystem, Long> {

    fun findByInspectionSiteId(inspectionSiteId: Long): List<CoatingSystem>


    @Query("SELECT CS.* FROM COATING_SYSTEM CS WHERE CS.INSPECTION_SITE_ID = :inspectionSiteId", nativeQuery = true)
    fun getCoatingSystemByInspectionId(@Param("inspectionSiteId") inspectionSiteId: Long): List<CoatingSystem>

}