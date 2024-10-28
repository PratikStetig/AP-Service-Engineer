package com.asianpaints.apse.service_engineer.dto

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus

data class ApprovalHistoryRequest(
    val status: InspectionSiteStatus,
    val remarks: String,
)