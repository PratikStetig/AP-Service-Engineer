package com.asianpaints.apse.service_engineer.dto


import javax.validation.constraints.*

data class CoatingSystemDto(
    val id: Long = 0,

    @field:NotBlank(message = "Coating system name cannot be blank")
    val coatingSystemName: String,

    @field:NotBlank(message = "Corrosivity level cannot be blank")
    val corrosivityLevel: String,  // Drop-down should be implemented on the frontend

    @field:NotBlank(message = "Type of structures cannot be blank")
    val typeOfStructures: String,

    @field:NotBlank(message = "Surface preparation cannot be blank")
    val surfacePreparation: String,

    @field:NotBlank(message = "SRFA bare metal cannot be blank")
    val srfaBareMetal: String,

    @field:NotNull(message = "Paint field cannot be null")
    val paint: Boolean,

    @field:NotNull(message = "Spray field cannot be null")
    val spray: Boolean,

    @field:DecimalMin(value = "0.0", inclusive = false, message = "WFT must be greater than 0")
    val wft: Double,

    @field:DecimalMin(value = "0.0", inclusive = false, message = "DFT must be greater than 0")
    val dft: Double,

    @field:NotNull(message = "Inspection report ID cannot be null")
    val inspectionReportId: Long,

    val products: List<Long>
)

