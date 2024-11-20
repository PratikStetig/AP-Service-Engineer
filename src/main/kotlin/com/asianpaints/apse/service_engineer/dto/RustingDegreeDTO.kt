package com.asianpaints.apse.service_engineer.dto

data class RustingDegreeDTO(
    val id: Long,
    val degreeOfRusting: String?,
    val rustedAreaPercentage: Float?,
    val rating: Int?,
    val sizeOfDefect: String?
)
