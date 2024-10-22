package com.asianpaints.apse.service_engineer.dto

import java.time.LocalDateTime

interface SiteAreaImageProjection {
    fun getId(): Long
    fun getSiteAreaId(): Long
    fun getImageUrl(): String
    fun getUploadedAt(): LocalDateTime
}
