package com.asianpaints.apse.service_engineer.mapper

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite
import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.dto.CoatingSystemResponse
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto
import java.util.stream.Collectors

object CoatingSystemMapper {

    fun toEntity(dto: CoatingSystemDto, inspectionReport: InspectionSite, products: MutableSet<ProductMaster>, siteAreas: Set<SiteArea>): CoatingSystem {
        return CoatingSystem(
            id = dto.id,
            coatingSystemName = dto.coatingSystemName,
            corrosivityLevel = dto.corrosivityLevel,
            typeOfStructures = dto.typeOfStructures,
            surfacePreparation = dto.surfacePreparation,
            srfaBareMetal = dto.srfaBareMetal,
            paint = dto.paint,
            spray = dto.spray,
            wft = dto.wft,
            dft = dto.dft,
            inspectionSiteId = inspectionReport,
            products = products,
            siteAreas = siteAreas,
        )
    }

    fun toDto(entity: CoatingSystem): CoatingSystemResponse {
        return CoatingSystemResponse(
            id = entity.id,
            coatingSystemName = entity.coatingSystemName,
            corrosivityLevel = entity.corrosivityLevel,
            typeOfStructures = entity.typeOfStructures,
            surfacePreparation = entity.surfacePreparation,
            srfaBareMetal = entity.srfaBareMetal,
            paint = entity.paint,
            spray = entity.spray,
            wft = entity.wft,
            dft = entity.dft,
            inspectionSiteId = entity.inspectionSiteId.id,
            products = entity.products,
            siteAreas = getAreas(entity.siteAreas)
        )
    }

    private fun getAreas(siteAreas: Set<SiteArea>): Set<SiteAreaDto> {
        return siteAreas.stream().map { siteArea: SiteArea ->
            SiteAreaDto().apply {
                this.id = siteArea.id
                this.coatingCondition = siteArea.coatingCondition
                this.corrosionType = siteArea.corrosionType
                this.rating = siteArea.rating
                this.area = siteArea.area
            }
        }.collect(Collectors.toSet())
    }
}
