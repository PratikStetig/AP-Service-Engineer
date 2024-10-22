package com.asianpaints.apse.service_engineer.dto

import java.time.LocalDateTime

data class SiteAreaImageDto(
    val id: Long,
    val imageUrl: String,
    val siteAreaId: Long,
    val uploadedAt: LocalDateTime
)

