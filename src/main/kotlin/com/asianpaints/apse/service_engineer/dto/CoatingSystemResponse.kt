package com.asianpaints.apse.service_engineer.dto

import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
data class CoatingSystemResponse(

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    val inspectionSiteId: Long? = null,
    val coatingSystemName: String? = null,
    val corrosivityLevel: String? = null,  // Drop-down field
    val typeOfStructures: String? = null,
    val surfacePreparation: String? = null,
    val srfaBareMetal: String? = null,
    val paint: Boolean? = null,
    val spray: Boolean? = null,
    val wft: Double? = null,
    val dft: Double? = null,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val products: Set<ProductMaster>? = emptySet(),

    val siteAreas: Set<SiteAreaDto>? = emptySet(),

    /*------------------ ADDITIONAL INFORMATION FOR RESPONSE ------------------------*/
    val possibleSurfacePreparation: String? = null,
    val recoatingInterval: String? = null,
    val serviceLife: String? = null,
    val shade: String? = null,
    val aesthetic: String? = null,
    val existingPaintingSystem: String? = null,
    val dftExistingSystem: String? = null,
    val remarks: String? = null
)
