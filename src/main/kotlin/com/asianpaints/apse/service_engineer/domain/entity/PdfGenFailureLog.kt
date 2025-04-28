package com.asianpaints.apse.service_engineer.domain.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class PdfGenFailureLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val inspectionId: Long,
    @Column(name = "reason", columnDefinition = "TEXT")
    val reason: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
