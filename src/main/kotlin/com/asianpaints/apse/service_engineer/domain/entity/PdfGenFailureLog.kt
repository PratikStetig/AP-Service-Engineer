package com.asianpaints.apse.service_engineer.domain.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class PdfGenFailureLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val inspectionId: Long,
    val reason: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
