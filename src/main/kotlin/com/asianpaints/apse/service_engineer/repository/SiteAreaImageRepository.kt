package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.SiteAreaImages
import com.asianpaints.apse.service_engineer.dto.SiteAreaImageProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SiteAreaImageRepository : JpaRepository<SiteAreaImages, Long> {

    @Query("SELECT sai.id as id, sai.site_area_id as siteAreaId, sai.image_url as imageUrl, sai.uploaded_at as uploadedAt from site_area_image sai WHERE sai.site_area_id = :siteAreaId", nativeQuery = true)
    fun findBySiteAreaId(@Param("siteAreaId") siteAreaId: Long): List<SiteAreaImageProjection>
}