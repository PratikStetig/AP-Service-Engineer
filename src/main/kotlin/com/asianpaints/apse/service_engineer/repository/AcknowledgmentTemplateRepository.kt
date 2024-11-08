package com.asianpaints.apse.service_engineer.repository

import com.asianpaints.apse.service_engineer.domain.entity.AcknowledgmentTemplate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AcknowledgmentTemplateRepository : JpaRepository<AcknowledgmentTemplate, Long> {

    @Query("SELECT t FROM AcknowledgmentTemplate t WHERE t.isActive = true")
    fun findActiveTemplate(): AcknowledgmentTemplate?

    @Modifying
    @Transactional
    @Query("UPDATE AcknowledgmentTemplate t SET t.isActive = false WHERE t.isActive = true")
    fun deactivateAllTemplates()
}
