package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.domain.entity.SiteAreaImages
import com.asianpaints.apse.service_engineer.dto.SiteAreaImageDto
import com.asianpaints.apse.service_engineer.dto.SiteAreaImageProjection
import com.asianpaints.apse.service_engineer.repository.SiteAreaImageRepository
import com.asianpaints.apse.service_engineer.repository.SiteAreaRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class SiteAreaImageService(
    private val siteAreaImageRepository: SiteAreaImageRepository,
    private val siteAreaRepository: SiteAreaRepository,
) {

    fun createSiteAreaImage(dto: SiteAreaImageDto): SiteAreaImages {
        val inspectionSiteArea = siteAreaRepository.findById(dto.siteAreaId).orElseThrow { throw IllegalArgumentException("Site area not found for Id ${dto.siteAreaId}") }
        val image = SiteAreaImages(siteAreaId = inspectionSiteArea!!, imageUrl = dto.imageUrl)
        return siteAreaImageRepository.save(image)
    }

    fun getSiteAreaImage(siteAreaId: Long): List<SiteAreaImageProjection> {
        return siteAreaImageRepository.findBySiteAreaId(siteAreaId)
    }

    fun deleteImage(id: Long) {
        if (!siteAreaImageRepository.existsById(id)) {
            throw EntityNotFoundException("Image with ID $id not found")
        }
        siteAreaImageRepository.deleteById(id)
    }

}
