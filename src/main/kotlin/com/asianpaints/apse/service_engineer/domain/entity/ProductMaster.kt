package com.asianpaints.apse.service_engineer.domain.entity

import javax.persistence.*


@Entity
@Table(name = "PRODUCT_MASTER")
data class ProductMaster(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "product_name")
    val productName: String,
    @Column(name = "product_spec")
    val productSpec: String,
    @Column(name = "volume_solids")
    val volumeSolids: Int,
    @Column(name = "mixing_ratio")
    val mixingRatio:String,
    @Column(name = "over_coating_interval")
    val overCoatingInterval: String,
)

