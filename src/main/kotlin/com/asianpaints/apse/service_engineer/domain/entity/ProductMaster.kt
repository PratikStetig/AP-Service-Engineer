package com.asianpaints.apse.service_engineer.domain.entity

import javax.persistence.*


@Entity
@Table(name = "PRODUCT_MASTER")
data class ProductMaster(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "product_name")
    val productName: String
)

