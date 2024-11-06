package com.asianpaints.apse.service_engineer.repository

import com.asianpaints.apse.service_engineer.domain.entity.ApprovalHistory
import com.asianpaints.apse.service_engineer.dto.ApprovalHistoryInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ApprovalHistoryRepository : JpaRepository<ApprovalHistory, Long> {
    fun findByInspectionSiteId(reportId: Long): List<ApprovalHistory>

    @Query(
        """
        SELECT ah.action as action, 
               ah.action_date as actionDate, 
               ah.remarks as remarks
               from approval_history ah
               where ah.inspection_site_id = :inspectionSiteId
               ORDER BY ah.action_date DESC
    """, nativeQuery = true
    )
    fun findByInspectionSiteIdComments(inspectionSiteId: Long): List<ApprovalHistoryInfo>
}