package com.asianpaints.apse.service_engineer.dto

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus
import java.time.LocalDateTime

/**
 * Projection for {@link com.asianpaints.apse.service_engineer.domain.entity.ApprovalHistory}
 */
interface ApprovalHistoryInfo {
    val actionDate: LocalDateTime
    val action: InspectionSiteStatus
    val remarks: String
}
