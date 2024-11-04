package com.asianpaints.apse.service_engineer.dto

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val products: List<CoatingSystemProductDetailsResponse>? = emptyList(),
    val siteAreas: Set<SiteAreaDto>? = emptySet(),
)
