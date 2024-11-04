package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDTO
import com.asianpaints.apse.service_engineer.dto.CoatingSystemProductDetailsDTO
import com.asianpaints.apse.service_engineer.dto.CoatingSystemProductDetailsResponse
import com.asianpaints.apse.service_engineer.dto.CoatingSystemResponse
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.mapper.CoatingSystemMapper
import com.asianpaints.apse.service_engineer.mapper.CoatingSystemProductDetailsMapper
import com.asianpaints.apse.service_engineer.repository.CoatingSystemRepository
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository
import com.asianpaints.apse.service_engineer.repository.ProductMasterRepository
import com.asianpaints.apse.service_engineer.repository.SiteAreaRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class CoatingSystemService(
    private val coatingSystemRepository: CoatingSystemRepository,
    private val inspectionSiteRepository: InspectionSiteRepository,
    private val productMasterRepository: ProductMasterRepository,
    private val siteAreaRepository: SiteAreaRepository,
    private val coatingSystemProductDetailsMapper: CoatingSystemProductDetailsMapper,
) {

//    @Transactional
//    fun addCoatingSystem(coatingSystemDto: CoatingSystemDto): CoatingSystem {
//        val inspectionSite = inspectionSiteRepository.findById(coatingSystemDto.inspectionSiteId).orElseThrow { throw InspectionSiteNotFound("Inspection Report not found") }
//        val productIds = coatingSystemDto.coatingSystemProductDetailsRequests.map { it.productId }.toList()
//        val products = productMasterRepository.findAllById(productIds)
//        if (products.size > 4) {
//            throw ProductLimitException("Cannot add more than 4 products")
//        }
//        val siteAreas: Set<SiteArea> = siteAreaRepository.findByIds(coatingSystemDto.areaIds)
//        if (siteAreas.isEmpty()) {
//            throw InspectionSiteNotFound(String.format("SiteArea with ids %s does not part of InspectionSite %s in system", coatingSystemDto.areaIds.toString(), inspectionSite.id))
//        }
//        for (siteArea in siteAreas) {
//            if (siteArea.inspectionSite.id != inspectionSite.id) {
//                val errMsg = String.format("SiteArea with id %s does not part of InspectionSite %s in system", siteArea.id, inspectionSite.id)
//                throw InspectionSiteNotFound(errMsg)
//            }
//        }
//
//        return coatingSystemRepository.save(
//            CoatingSystemMapper.toEntity(
//                coatingSystemDto,
//                inspectionSite,
//                coatingSystemDto.coatingSystemProductDetailsRequests.toMutableSet(),
//                siteAreas
//            )
//        );
//    }

    @Transactional
    fun addProductDetails(coatingSystemId: Long, request: CoatingSystemProductDetailsDTO): CoatingSystemProductDetailsResponse {
        val coatingSystem = coatingSystemRepository.findById(coatingSystemId)
            .orElseThrow { EntityNotFoundException("Coating system not found") }

        val product = productMasterRepository.findById(request.productId)
            .orElseThrow { EntityNotFoundException("Product not found") }

        val entity = coatingSystemProductDetailsMapper.toEntity(request, coatingSystem, product)
        coatingSystem.productDetails.add(entity)

        val savedCoatingSystem = coatingSystemRepository.save(coatingSystem)
        val savedDetails = savedCoatingSystem.productDetails
            .first { it.product.id == request.productId && it.layerOrder == request.layerOrder }

        return coatingSystemProductDetailsMapper.toResponse(savedDetails)
    }


    fun getAllCoatingSystemByInspectionId(inspectionId: Long): List<CoatingSystemResponse> {
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
    fun updateCoatingSystem(id: Long, coatingSystemDto: CoatingSystemDTO): CoatingSystemResponse {
        val existingCoatingSystem = coatingSystemRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("Coating System not found with id: $id")
        }

        existingCoatingSystem.apply {
            coatingSystemName = coatingSystemDto.coatingSystemName
            corrosivityLevel = coatingSystemDto.corrosivityLevel
            typeOfStructures = coatingSystemDto.typeOfStructures
            surfacePreparation = coatingSystemDto.surfacePreparation
            srfaBareMetal = coatingSystemDto.srfaBareMetal
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

//        if (coatingSystem.getOrderedProducts().contains(product)) {
        coatingSystem.removeProduct(product)
        coatingSystemRepository.save(coatingSystem)
//        } else {
//            throw EntityNotFoundException("Product is not associated with this Coating System")
//        }
    }


    @Transactional
    fun addProductToCoatingSystem(coatingSystemId: Long, productId: Long) {
        val coatingSystem = coatingSystemRepository.findById(coatingSystemId).orElseThrow {
            throw EntityNotFoundException("Coating System not found with id: $coatingSystemId")
        }

        val product = productMasterRepository.findById(productId).orElseThrow {
            throw EntityNotFoundException("Product not found with id: $productId")
        }


//        if (!coatingSystem.products.contains(product)) {
//            coatingSystem.products.add(product)  // Add the product to the list
        coatingSystem.addProduct(
            product = product,
            wftMin = 100,
            wftMax = 150,
            dft = 75.0,
            paint = true,
            spray = true,
            layerOrder = 1
        )
        coatingSystemRepository.save(coatingSystem)
//        } else {
//            throw IllegalArgumentException("Product already added to this Coating System")
//        }
    }


    @Transactional
    fun addCoatingSystem(dto: CoatingSystemDTO): CoatingSystemResponse {
        // Fetch related entities like InspectionSite and SiteAreas
        val inspectionSite = inspectionSiteRepository.findById(dto.inspectionSiteId)
            .orElseThrow { IllegalArgumentException("Invalid inspection site ID: ${dto.inspectionSiteId}") }

        val siteAreas = siteAreaRepository.findAllById(dto.siteAreaIds)

        // Create a new CoatingSystem entity
        val coatingSystem = CoatingSystem(
            coatingSystemName = dto.coatingSystemName,
            corrosivityLevel = dto.corrosivityLevel,
            typeOfStructures = dto.typeOfStructures,
            surfacePreparation = dto.surfacePreparation,
            srfaBareMetal = dto.srfaBareMetal,
            inspectionSiteId = inspectionSite,
            siteAreas = siteAreas.toSet()
        )

        // Map and add product details
        dto.productDetails.forEach { productDetailDTO ->
            val product = productMasterRepository.findById(productDetailDTO.productId)
                .orElseThrow { IllegalArgumentException("Invalid product ID: ${productDetailDTO.productId}") }

            coatingSystem.addProduct(
                product = product,
                wftMin = productDetailDTO.wftMin,
                wftMax = productDetailDTO.wftMax,
                dft = productDetailDTO.dft,
                layerOrder = productDetailDTO.layerOrder,
                paint = productDetailDTO.paint,
                spray = productDetailDTO.spray
            )
        }

        // Save the CoatingSystem entity to the database
        val savedCoatingSystem = coatingSystemRepository.save(coatingSystem)

        // Return the response
        return CoatingSystemResponse(
            id = savedCoatingSystem.id,
            coatingSystemName = savedCoatingSystem.coatingSystemName,
            corrosivityLevel = savedCoatingSystem.corrosivityLevel,
            typeOfStructures = savedCoatingSystem.typeOfStructures,
            surfacePreparation = savedCoatingSystem.surfacePreparation,
            srfaBareMetal = savedCoatingSystem.srfaBareMetal
        )
    }

}