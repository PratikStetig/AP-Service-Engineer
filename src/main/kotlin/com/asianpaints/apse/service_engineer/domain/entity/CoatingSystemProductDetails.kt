package com.asianpaints.apse.service_engineer.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "COATING_SYSTEM_PRODUCT_DETAILS")
data class CoatingSystemProductDetails(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coating_system_id")
    @JsonIgnore
    val coatingSystem: CoatingSystem,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    val product: ProductMaster,

    @Column(name = "wft_min")
    val wftMin: Int,

    @Column(name = "wft_max")
    val wftMax: Int,

    @Column(name = "dft")
    val dft: Double,

    @Column(name = "paint")
    val paint: Boolean,

    @Column(name = "spray")
    val spray: Boolean,

    @Column(name = "layer_order")
    val layerOrder: Int
)
