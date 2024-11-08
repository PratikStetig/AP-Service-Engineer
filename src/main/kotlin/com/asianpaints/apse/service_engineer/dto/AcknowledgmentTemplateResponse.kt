package com.asianpaints.apse.service_engineer.dto

data class AcknowledgmentTemplateResponse(
    val id: Long,
    val templateContent: String,
    val isActive: Boolean
)
