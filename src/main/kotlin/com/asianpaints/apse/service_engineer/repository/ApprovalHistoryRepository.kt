package com.asianpaints.apse.service_engineer.repository

import com.asianpaints.apse.service_engineer.domain.entity.ApprovalHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ApprovalHistoryRepository : JpaRepository<ApprovalHistory, Long> {
    fun findByInspectionSiteId(reportId: Long): List<ApprovalHistory>
}