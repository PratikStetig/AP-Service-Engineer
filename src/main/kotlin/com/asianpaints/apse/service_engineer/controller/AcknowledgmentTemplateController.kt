package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.dto.AcknowledgmentTemplateRequest
import com.asianpaints.apse.service_engineer.dto.AcknowledgmentTemplateResponse
import com.asianpaints.apse.service_engineer.service.AcknowledgmentTemplateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/templates")
class AcknowledgmentTemplateController(
    private val service: AcknowledgmentTemplateService
) {

    @PostMapping("/acknowledgment")
    fun createTemplate(@RequestBody request: AcknowledgmentTemplateRequest): ResponseEntity<AcknowledgmentTemplateResponse> {
        val response = service.createTemplate(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/acknowledgment")
    fun getAllTemplate(): ResponseEntity<List<AcknowledgmentTemplateResponse>> {
        val response = service.getAllTemplates()
        return ResponseEntity.ok(response)
    }


    @GetMapping("/acknowledgment/active")
    fun getActiveTemplate(): ResponseEntity<AcknowledgmentTemplateResponse> {
        val response = service.getActiveTemplate()
        return ResponseEntity.ok(response)
    }

    @GetMapping("acknowledgment/{id}")
    fun getTemplate(@PathVariable id: Long): ResponseEntity<AcknowledgmentTemplateResponse> {
        val response = service.getTemplate(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @PutMapping("acknowledgment/{id}")
    fun updateTemplate(@PathVariable id: Long, @RequestBody request: AcknowledgmentTemplateRequest): ResponseEntity<AcknowledgmentTemplateResponse> {
        val response = service.updateTemplate(id, request) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("acknowledgment/{id}")
    fun deleteTemplate(@PathVariable id: Long): ResponseEntity<Void> {
        service.deleteTemplate(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("acknowledgment/{id}/activate")
    fun activateTemplate(@PathVariable id: Long): ResponseEntity<AcknowledgmentTemplateResponse> {
        val response = service.activateTemplate(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(response)
    }
}
