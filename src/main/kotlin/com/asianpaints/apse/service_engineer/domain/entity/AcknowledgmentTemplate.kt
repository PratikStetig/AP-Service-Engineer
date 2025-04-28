package com.asianpaints.apse.service_engineer.domain.entity

import javax.persistence.*

@Entity
@Table(name = "acknowledgment_templates")
data class AcknowledgmentTemplate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, columnDefinition = "TEXT")
    val templateContent: String,

    @Column(nullable = false)
    val isActive: Boolean = false
)
