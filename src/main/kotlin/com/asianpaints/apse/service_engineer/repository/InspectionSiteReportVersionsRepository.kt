package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteReportVersions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface InspectionSiteReportVersionsRepository : JpaRepository<InspectionSiteReportVersions, Long> {
    @Query("SELECT COALESCE(MAX(r.versionNumber), 0) FROM InspectionSiteReportVersions r WHERE r.inspectionSiteId.id = :inspectionSiteId AND r.deleted = false")
    fun findLatestVersionNumberByInspectionSiteId(@Param("inspectionSiteId") inspectionSiteId: Long?): Int?

//    @Query("SELECT r FROM InspectionSiteReportVersions r WHERE r.inspectionSiteId.id = :inspectionSiteId AND r.deleted = false ORDER BY r.versionNumber DESC")
//    fun findLatestReportByInspectionSiteId(@Param("inspectionSiteId") inspectionSiteId: Long?): Optional<InspectionSiteReportVersions?>?

    fun findFirstByInspectionSiteIdIdAndDeletedFalseOrderByVersionNumberDesc(inspectionSiteId: Long?): Optional<InspectionSiteReportVersions?>?
}