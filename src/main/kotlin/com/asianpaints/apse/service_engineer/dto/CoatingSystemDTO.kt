package com.asianpaints.apse.service_engineer.dto


import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CoatingSystemDTO(
    val id: Long = 0,
    @field:NotBlank(message = "Coating system name cannot be blank")
    var coatingSystemName: String,
    @field:NotBlank(message = "Corrosivity level cannot be blank")
    var corrosivityLevel: String,  // Drop-down should be implemented on the frontend
    @field:NotBlank(message = "Type of structures cannot be blank")
    var typeOfStructures: String,
    @field:NotBlank(message = "Surface preparation cannot be blank")
    var surfacePreparation: String,
    @field:NotBlank(message = "SRFA bare metal cannot be blank")
    var srfaBareMetal: String,
    @field:NotNull(message = "Inspection site ID cannot be null")
    val inspectionSiteId: Long,
    val productDetails: List<CoatingSystemProductDetailsDTO>,
    val siteAreaIds: Set<Long>,
)

