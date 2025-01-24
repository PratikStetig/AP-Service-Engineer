package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.service.PdfGenerationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult


@RestController
@RequestMapping("/pdf")
class PdfController @Autowired constructor(
    private val pdfGenService: PdfGenerationService,
) {

    @GetMapping("/preview/{inspectionId}")
    fun getPdfPreview(@PathVariable inspectionId: Long): ResponseEntity<ByteArray> {
        return pdfGenService.previewPdfBytes(inspectionId)
    }

    @GetMapping("/generate/{inspectionId}")
    fun generatePdf(@PathVariable inspectionId: Long): DeferredResult<ResponseEntity<ByteArray>> {
//        return pdfGenService.generatePdfAsync(inspectionId)
        return pdfGenService.generatePdfAsync(inspectionId)
    }
}
