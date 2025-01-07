package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.mapper.CoatingSystemMapper
import com.asianpaints.apse.service_engineer.mapper.SiteCorrosivityEnvironmentMapper
import com.asianpaints.apse.service_engineer.repository.*
import com.asianpaints.apse.service_engineer.service.PdfGenerationService
import com.asianpaints.apse.service_engineer.util.PageCounterUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.util.concurrent.CompletableFuture


@RestController
@RequestMapping("/pdf")
class PdfController @Autowired constructor(
    private val pdfGenService: PdfGenerationService,
) {

    @GetMapping("/preview/{inspectionId}")
    fun getPdfPreview(@PathVariable inspectionId: Long): ResponseEntity<ByteArray> {
        return pdfGenService.generatePdfAsyncBytes(inspectionId)
    }

    @GetMapping("/generate/{inspectionId}")
    fun generatePdf(@PathVariable inspectionId: Long): DeferredResult<ResponseEntity<ByteArray>> {
//        return pdfGenService.generatePdfAsync(inspectionId)
        return pdfGenService.generatePdfAsync(inspectionId)
    }
}
