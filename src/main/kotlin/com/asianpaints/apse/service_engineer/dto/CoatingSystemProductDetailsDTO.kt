package com.asianpaints.apse.service_engineer.dto

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class CoatingSystemProductDetailsDTO(
    val productId: Long,
    @field:Min(0, message = "WFT min must be greater than or equal to 0")
    val wftMin: Int,
    @field:Min(0, message = "WFT max must be greater than or equal to 0")
    val wftMax: Int,
    @field:Min(0, message = "DFT must be greater than or equal to 0")
    val dft: Double,
    @field:Min(0, message = "Layer order must be greater than or equal to 0")
    val layerOrder: Int,
    @field:NotNull(message = "Paint field cannot be null")
    var paint: Boolean,
    @field:NotNull(message = "Spray field cannot be null")
    var spray: Boolean,
) {
    @AssertTrue(message = "WFT max must be greater than WFT min")
    fun isWftMaxGreaterThanMin(): Boolean {
        return wftMax > wftMin
    }
}