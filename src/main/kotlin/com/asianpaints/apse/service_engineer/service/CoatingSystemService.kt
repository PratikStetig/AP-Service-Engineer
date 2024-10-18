package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.ProductLimitException
import com.asianpaints.apse.service_engineer.mapper.CoatingSystemMapper
import com.asianpaints.apse.service_engineer.repository.CoatingSystemRepository
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository
import com.asianpaints.apse.service_engineer.repository.ProductMasterRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class CoatingSystemService(
    private val coatingSystemRepository: CoatingSystemRepository,
    private val inspectionSiteRepository: InspectionSiteRepository,
    private val productMasterRepository: ProductMasterRepository,
) {

    fun addCoatingSystem(coatingSystemDto: CoatingSystemDto): CoatingSystem {
        val inspectionReport = inspectionSiteRepository.findById(coatingSystemDto.inspectionReportId).orElseThrow { throw InspectionSiteNotFound("Inspection Report not found") }

        val products = productMasterRepository.findAllById(coatingSystemDto.products)

        if (products.size > 4) throw ProductLimitException("Cannot add more than 4 products")

        val coatingSystem = CoatingSystemMapper.toEntity(coatingSystemDto, inspectionReport, products)

        return coatingSystemRepository.save(coatingSystem)
    }


    fun getAllCoatingSystemByInspectionId(inspectionId: Long): List<CoatingSystemDto> {
        inspectionSiteRepository.findById(inspectionId).orElseThrow {
            throw InspectionSiteNotFound(String.format("InspectionSite with id %s does not exist in system", inspectionId))
        }
        val inspectionSiteAcknowledgements: List<CoatingSystem> = coatingSystemRepository.getCoatingSystemByInspectionId(inspectionId)
        return inspectionSiteAcknowledgements.map { CoatingSystemMapper.toDto(it) }
    }

    @Transactional
    fun deleteCoatingSystem(id: Long) {
        val coatingSystem = coatingSystemRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Coating System not found with id: $id")
        }
        coatingSystemRepository.delete(coatingSystem)
    }



    @Transactional
    fun updateCoatingSystem(id: Long, coatingSystemDto: CoatingSystemDto): CoatingSystemDto {
        val existingCoatingSystem = coatingSystemRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Coating System not found with id: $id")
        }

        existingCoatingSystem.apply {
            coatingSystemName = coatingSystemDto.coatingSystemName
            corrosivityLevel = coatingSystemDto.corrosivityLevel
            typeOfStructures = coatingSystemDto.typeOfStructures
            surfacePreparation = coatingSystemDto.surfacePreparation
            srfaBareMetal = coatingSystemDto.srfaBareMetal
            paint = coatingSystemDto.paint
            spray = coatingSystemDto.spray
            wft = coatingSystemDto.wft
            dft = coatingSystemDto.dft
        }

        return CoatingSystemMapper.toDto(coatingSystemRepository.save(existingCoatingSystem))
    }


    @Transactional
    fun removeProductFromCoatingSystem(coatingSystemId: Long, productId: Long) {
        val coatingSystem = coatingSystemRepository.findById(coatingSystemId).orElseThrow {
            throw EntityNotFoundException("Coating System not found with id: $coatingSystemId")
        }

        val product = productMasterRepository.findById(productId).orElseThrow {
            throw EntityNotFoundException("Product not found with id: $productId")
        }

        if (coatingSystem.products.contains(product)) {
            coatingSystem.products.remove(product)
            coatingSystemRepository.save(coatingSystem)
        } else {
            throw EntityNotFoundException("Product is not associated with this Coating System")
        }
    }


    @Transactional
    fun addProductToCoatingSystem(coatingSystemId: Long, productId: Long) {
        val coatingSystem = coatingSystemRepository.findById(coatingSystemId).orElseThrow {
            throw EntityNotFoundException("Coating System not found with id: $coatingSystemId")
        }

        val product = productMasterRepository.findById(productId).orElseThrow {
            throw EntityNotFoundException("Product not found with id: $productId")
        }

        if (!coatingSystem.products.contains(product)) {
            coatingSystem.products.add(product)  // Add the product to the list
            coatingSystemRepository.save(coatingSystem)
        } else {
            throw IllegalArgumentException("Product already added to this Coating System")
        }
    }

}
