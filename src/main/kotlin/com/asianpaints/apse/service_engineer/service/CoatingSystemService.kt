package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.ProductLimitException
import com.asianpaints.apse.service_engineer.mapper.CoatingSystemMapper
import com.asianpaints.apse.service_engineer.repository.CoatingSystemRepository
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository
import com.asianpaints.apse.service_engineer.repository.ProductMasterRepository
import org.springframework.stereotype.Service

@Service
class CoatingSystemService(
    private val coatingSystemRepository: CoatingSystemRepository,
    private val inspectionReportRepository: InspectionSiteRepository,
    private val productMasterRepository: ProductMasterRepository
) {

    fun addCoatingSystem(coatingSystemDto: CoatingSystemDto): CoatingSystem {
        val inspectionReport = inspectionReportRepository.findById(coatingSystemDto.inspectionReportId).orElseThrow { throw InspectionSiteNotFound("Inspection Report not found") }

        val products = productMasterRepository.findAllById(coatingSystemDto.products)

        if (products.size > 4) throw ProductLimitException("Cannot add more than 4 products")

        val coatingSystem = CoatingSystemMapper.toEntity(coatingSystemDto, inspectionReport, products)

        return coatingSystemRepository.save(coatingSystem)
    }
}
