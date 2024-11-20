package com.asianpaints.apse.service_engineer.domain.entity

import javax.persistence.*

@Entity
@Table(name = "TOOL_TIP_INFO")
data class ToolTipInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: ToolTipCategory,
    val degreeOfRusting: String?,
    val rustedAreaPercentage: Float?,
    val rating: Int,
    val sizeOfDefect: String?,
    val quantityOfDefect: String?,
    val areaPercentage: String?,
    val distribution: String?,
    val flakedArea: Float?,
    val sizeOfFlakedAreas: String?
)
