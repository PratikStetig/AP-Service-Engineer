package com.asianpaints.apse.service_engineer.mapper

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite
import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto

object CoatingSystemMapper {

    fun toEntity(dto: CoatingSystemDto, inspectionReport: InspectionSite, products: List<ProductMaster>): CoatingSystem {
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
            products = products
        )
    }

    fun toDto(entity: CoatingSystem): CoatingSystemDto {
        return CoatingSystemDto(
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
            inspectionReportId = entity.inspectionSiteId.id,
            products = entity.products.map { it.id }
        )
    }
}
