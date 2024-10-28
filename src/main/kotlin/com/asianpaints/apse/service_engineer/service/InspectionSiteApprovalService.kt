package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.domain.entity.ApprovalHistory
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite
import com.asianpaints.apse.service_engineer.dto.ApprovalHistoryRequest
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException
import com.asianpaints.apse.service_engineer.repository.ApUserRepository
import com.asianpaints.apse.service_engineer.repository.ApprovalHistoryRepository
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class InspectionSiteApprovalService(
    private val inspectionSiteRepository: InspectionSiteRepository,
    private val apUserRepository: ApUserRepository,
    private val approvalHistoryRepository: ApprovalHistoryRepository
) {

//    @Transactional
//    fun lockReportForViewing(reportId: Long, approverId: String): InspectionReport {
//        val report = inspectionReportRepository.findById(reportId)
//            .orElseThrow { IllegalArgumentException("Inspection report not found") }
//
//        if (report.lockedBy != null && report.lockedBy != approverId) {
//            throw IllegalStateException("Report is currently being viewed by another approver.")
//        }
//
//        report.lockedBy = approverId
//        return inspectionReportRepository.save(report)
//    }

    @Transactional
    fun updateInspectionReportStatus(inspectionSiteId: Long, approverId: Long, approvalHistoryRequest: ApprovalHistoryRequest): InspectionSite {
        val inspectionReport = inspectionSiteRepository.findById(inspectionSiteId).orElseThrow { InspectionSiteNotFound("Inspection report not found Id $inspectionSiteId") }
        val isValidUser = apUserRepository.existsByIdAndUserDesignation_IdAndIsActive(approverId, 6, true)
        if (isValidUser) {
            inspectionReport.status = approvalHistoryRequest.status
            val inspectionSite = inspectionSiteRepository.save(inspectionReport)
            val approvalHistory = ApprovalHistory(
                inspectionSite = inspectionSite,
                approverId = approverId,
                action = approvalHistoryRequest.status,
                remarks = approvalHistoryRequest.remarks
            )
            approvalHistoryRepository.save(approvalHistory)
            return inspectionSite
        } else {
            throw UserNotFoundException("User not found by Id $approverId or not having enough rights")
        }
    }


//    fun releaseLock(reportId: Long, approverId: String) {
//        val report = inspectionReportRepository.findById(reportId)
//            .orElseThrow { IllegalArgumentException("Inspection report not found") }
//
//        if (report.lockedBy == approverId) {
//            report.lockedBy = null
//            inspectionReportRepository.save(report)
//        }
//    }
}