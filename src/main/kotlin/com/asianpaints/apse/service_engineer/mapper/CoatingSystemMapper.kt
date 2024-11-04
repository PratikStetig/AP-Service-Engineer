package com.asianpaints.apse.service_engineer.mapper

import com.asianpaints.apse.service_engineer.domain.entity.*
import com.asianpaints.apse.service_engineer.dto.*
import java.util.stream.Collectors

object CoatingSystemMapper {

    fun toEntity(dto: CoatingSystemDTO, inspectionReport: InspectionSite, products: MutableSet<CoatingSystemProductDetailsDTO>, siteAreas: Set<SiteArea>): CoatingSystem {
        return CoatingSystem(
            id = dto.id,
            coatingSystemName = dto.coatingSystemName,
            corrosivityLevel = dto.corrosivityLevel,
            typeOfStructures = dto.typeOfStructures,
            surfacePreparation = dto.surfacePreparation,
            srfaBareMetal = dto.srfaBareMetal,
            inspectionSiteId = inspectionReport,
//            productDetails = convertToEntityList(coatingSystem = dto, products, ),
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
            inspectionSiteId = entity.inspectionSiteId.id,
            products = entity.productDetails.map {
                CoatingSystemProductDetailsResponse(
                    id = it.id,
                    productId = it.product.id,
                    productName = it.product.productName,
                    productSpec = it.product.productSpec,
                    volumeSolids = it.product.volumeSolids,
                    mixingRatio = it.product.mixingRatio,
                    overCoatingInterval = it.product.overCoatingInterval,
                    wftMin = it.wftMin,
                    wftMax = it.wftMax,
                    dft = it.dft,
                    layerOrder = it.layerOrder,
                    paint = it.paint,
                    spray = it.spray,
                )
            },
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
