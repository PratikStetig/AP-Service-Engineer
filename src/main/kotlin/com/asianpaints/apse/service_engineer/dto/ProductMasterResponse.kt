package com.asianpaints.apse.service_engineer.dto

data class ProductMasterResponse(
    val id: Long,
    val productName: String,
    val productSpec: String,
    val volumeSolids: Int,
    val mixingRatio: String,
    val overCoatingInterval: String
)
