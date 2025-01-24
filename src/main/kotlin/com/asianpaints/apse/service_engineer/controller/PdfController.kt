package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.service.PdfGenerationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.ModelAndView


@RestController
@RequestMapping("/pdf")
class PdfController @Autowired constructor(
    private val pdfGenService: PdfGenerationService,
) {

    @GetMapping("/preview/{inspectionId}")
    fun getPdfPreview(@PathVariable inspectionId: Long): ResponseEntity<ByteArray> {
        return pdfGenService.generatePdfAsyncBytes(inspectionId)
    }

    @GetMapping("/sample")
    fun getSamplePage(model: Model): ModelAndView {
        val items: MutableList<Item> = ArrayList<Item>()
        items.add(Item("Dynamic Title 1", "/images/sample1.jpg", "This is a description for item 1."))
        items.add(Item("Dynamic Title 2", "/images/sample2.jpg", "This is a description for item 2."))
        items.add(Item("Dynamic Title 3", "/images/sample3.jpg", "This is a description for item 3."))
        model.addAttribute("items", items)
        return ModelAndView("pdf_template_preview", model.asMap())
    }


    internal class Item(val title: String, val image: String, val description: String)


//    @GetMapping("/preview/{inspectionId}")
//    fun getPdfPreview(@PathVariable inspectionId: Long): ResponseEntity<ByteArray> {
//        return pdfGenService.generatePdfAsyncBytes(inspectionId)
//    }

    @GetMapping("/generate/{inspectionId}")
    fun generatePdf(@PathVariable inspectionId: Long): DeferredResult<ResponseEntity<ByteArray>> {
//        return pdfGenService.generatePdfAsync(inspectionId)
        return pdfGenService.generatePdfAsync(inspectionId)
    }
}
