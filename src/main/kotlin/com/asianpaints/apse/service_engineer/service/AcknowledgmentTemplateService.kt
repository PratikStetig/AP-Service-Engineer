package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.domain.entity.AcknowledgmentTemplate
import com.asianpaints.apse.service_engineer.dto.AcknowledgmentTemplateRequest
import com.asianpaints.apse.service_engineer.dto.AcknowledgmentTemplateResponse
import com.asianpaints.apse.service_engineer.repository.AcknowledgmentTemplateRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AcknowledgmentTemplateService(
    private val repository: AcknowledgmentTemplateRepository
) {

    fun createTemplate(request: AcknowledgmentTemplateRequest): AcknowledgmentTemplateResponse {
        val template = AcknowledgmentTemplate(
            templateContent = request.templateContent,
            isActive = false
        )
        return repository.save(template).toResponse()
    }

    fun getActiveTemplate(): AcknowledgmentTemplateResponse? {
        return repository.findActiveTemplate()?.toResponse()
    }

    fun getAllTemplates(): List<AcknowledgmentTemplateResponse> {
        return repository.findAll().map { it.toResponse() }
    }

    fun getTemplate(id: Long): AcknowledgmentTemplateResponse? {
        return repository.findById(id).orElse(null)?.toResponse()
    }

    fun updateTemplate(id: Long, request: AcknowledgmentTemplateRequest): AcknowledgmentTemplateResponse? {
        val template = repository.findById(id).orElse(null) ?: return null
        val updatedTemplate = template.copy(
            templateContent = request.templateContent
        )
        return repository.save(updatedTemplate).toResponse()
    }

    fun deleteTemplate(id: Long) {
        repository.deleteById(id)
    }

    @Transactional
    fun activateTemplate(id: Long): AcknowledgmentTemplateResponse? {
        repository.deactivateAllTemplates()
        val template = repository.findById(id).orElse(null) ?: return null
        val activatedTemplate = template.copy(isActive = true)
        return repository.save(activatedTemplate).toResponse()
    }

    private fun AcknowledgmentTemplate.toResponse() = AcknowledgmentTemplateResponse(
        id = id,
        templateContent = templateContent,
        isActive = isActive
    )
}
