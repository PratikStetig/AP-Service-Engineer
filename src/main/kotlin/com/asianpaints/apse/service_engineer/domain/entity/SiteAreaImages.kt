package com.asianpaints.apse.service_engineer.domain.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "site_area_image")
data class SiteAreaImages(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.DETACH])
    @JsonBackReference
    @JoinColumn(name = "site_area_id", nullable = false)

    val siteArea: SiteArea,

    @Column(name = "image_url", length = 500, nullable = false)
    val imageUrl: String,

    @Column(name = "uploaded_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val uploadedAt: LocalDateTime = LocalDateTime.now()
)
