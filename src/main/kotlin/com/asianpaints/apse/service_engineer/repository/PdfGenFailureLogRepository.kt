package com.asianpaints.apse.service_engineer.repository

import com.asianpaints.apse.service_engineer.domain.entity.PdfGenFailureLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface PdfGenFailureLogRepository : JpaRepository<PdfGenFailureLog, Long>
