package com.asianpaints.apse.service_engineer.mapper

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystemProductDetails
import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster
import com.asianpaints.apse.service_engineer.dto.CoatingSystemProductDetailsDTO
import com.asianpaints.apse.service_engineer.dto.CoatingSystemProductDetailsResponse
import com.asianpaints.apse.service_engineer.dto.ProductMasterResponse
import org.springframework.stereotype.Component


@Component
class CoatingSystemProductDetailsMapper {
    fun toEntity(
        request: CoatingSystemProductDetailsDTO,
        coatingSystem: CoatingSystem,
        product: ProductMaster
    ): CoatingSystemProductDetails {
        return CoatingSystemProductDetails(
            coatingSystem = coatingSystem,
            product = product,
            wftMin = request.wftMin,
            wftMax = request.wftMax,
            dft = request.dft,
            spray = request.spray,
            paint = request.paint,
            layerOrder = request.layerOrder
        )
    }

    fun toResponse(entity: CoatingSystemProductDetails): CoatingSystemProductDetailsResponse {
        return CoatingSystemProductDetailsResponse(
            id = entity.id,
            wftMin = entity.wftMin,
            wftMax = entity.wftMax,
            dft = entity.dft,
            layerOrder = entity.layerOrder,
            paint = entity.paint,
            spray = entity.spray,
            productId = entity.product.id,
            productName = entity.product.productName,
            productSpec = entity.product.productSpec,
            volumeSolids = entity.product.volumeSolids,
            mixingRatio = entity.product.mixingRatio,
            overCoatingInterval = entity.product.overCoatingInterval
        )
    }

    private fun toProductResponse(product: ProductMaster): ProductMasterResponse {
        return ProductMasterResponse(
            id = product.id,
            productName = product.productName,
            productSpec = product.productSpec,
            volumeSolids = product.volumeSolids,
            mixingRatio = product.mixingRatio,
            overCoatingInterval = product.overCoatingInterval
        )
    }
}