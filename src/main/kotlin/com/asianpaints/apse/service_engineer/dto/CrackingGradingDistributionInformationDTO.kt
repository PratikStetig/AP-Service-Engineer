package com.asianpaints.apse.service_engineer.dto

data class CrackingGradingDistributionInformationDTO(
    val id: Long,
    val rating: Int?,
    val sizeOfDefect: String?,
    val quantityOfDefect: String?,
    val areaPercentage: String?,
    val distribution: String?
)
