
package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.service.PdfService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/pdf")
class PdfController @Autowired constructor(
    private val templateEngine: SpringTemplateEngine,
    private val pdfService: PdfService
) {

    @GetMapping("/generate")
    fun generatePdf(model: Model): ResponseEntity<ByteArray> {
        val context = Context().apply {
            setVariable("name", "John Doe")
            setVariable("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            setVariable("items", listOf("Item 1", "Item 2", "Item 3"))
        }

        // Render the HTML content using Thymeleaf
        val htmlContent = templateEngine.process("sample", context)

        // Convert the rendered HTML to PDF
        val pdfBytes = pdfService.convertHtmlToPdfBytes(htmlContent)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes)
    }
}
