package com.asianpaints.apse.service_engineer.dto

data class CoatingSystemProductDetailsResponse(
    val id: Long,
    val wftMin: Int,
    val wftMax: Int,
    val dft: Double,
    val layerOrder: Int,
    val paint: Boolean,
    val spray: Boolean,
    val productId: Long,
    val productName: String,
    val productSpec: String,
    val volumeSolids: Int,
    val mixingRatio: String,
    val overCoatingInterval: String
) {
    fun modeOfApplication(): String {
        return if (paint) "Paint" else if (spray) "Spray" else "NA"
    }
}
