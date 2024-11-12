package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement
import com.asianpaints.apse.service_engineer.repository.InspectionSiteAcknowledgmentRepository
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository
import com.asianpaints.apse.service_engineer.repository.SitePreliminaryObservationRepository
import com.asianpaints.apse.service_engineer.service.InspectionSiteService
import com.asianpaints.apse.service_engineer.service.PdfService
import com.sun.org.apache.xpath.internal.operations.Bool
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
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/pdf")
class PdfController @Autowired constructor(
    private val templateEngine: SpringTemplateEngine,
    private val pdfService: PdfService,
    private val inspectionSiteRepository: InspectionSiteRepository,
    private val acknowledgmentRepository: InspectionSiteAcknowledgmentRepository,
    private val sitePreliminaryObservationRepository: SitePreliminaryObservationRepository
) {

    @GetMapping("/preview/{inspectionId}")
    fun getPdfPreview(@PathVariable inspectionId: Long, model: Model): String {
        val inspectionSite = inspectionSiteRepository.findById(inspectionId)
        val acknowledgmentInfo = acknowledgmentRepository.findByInspectionSiteId(inspectionId)
        val acknowledgmentPersons = acknowledgmentInfo.map { "${it.personName} ${it.designation}" }.toList()
        val preObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionId).get()

        print("Image is ${inspectionSite.get().imageUrl}")
        val context = Context().apply {
            /*----------------------MainPage----------------------*/
            setVariable("reportName", inspectionSite.get().reportName)
            setVariable("conductedAt", inspectionSite.get().conductedAt)
            setVariable("siteImage", inspectionSite.get().imageUrl)
            setVariable("certificateNo", "STATIC")
            setVariable("inspectionDate", inspectionSite.get().inspectionDate)
            setVariable("conductedBy", inspectionSite.get().conductedBy.name)


            /*----------------------AcknowledgementPage----------------------*/
            setVariable("items", acknowledgmentPersons)


            /*----------------------PreliminaryObservation----------------------*/
            setVariable("ruralArea", getYesNo(preObservation.ruralArea))
            setVariable("urbanArea", getYesNo(preObservation.urbanArea))
            setVariable("coastalArea", getYesNo(preObservation.coastalArea))
            setVariable("industrialPollutedArea", getYesNo(preObservation.industrialPollutedArea))
            setVariable("chemicalExposed", preObservation.chemicalsExposed)
            setVariable("avgHumidity", preObservation.averageHumidity)
            setVariable("salineAtmosphere", preObservation.salineAtmosphere)

        }

        // Render the HTML content using Thymeleaf
        val htmlContent = templateEngine.process("pdf_template", context)

        return htmlContent
    }


    @GetMapping("/generate/{inspectionId}")
    fun generatePdf(@PathVariable inspectionId: Long, model: Model): ResponseEntity<ByteArray> {
        val inspectionSite = inspectionSiteRepository.findById(inspectionId)
        val acknowledgmentInfo = acknowledgmentRepository.findByInspectionSiteId(inspectionId)
        val acknowledgmentPersons = acknowledgmentInfo.map { "${it.personName} ${it.designation}" }.toList()
        val preObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionId).get()

        val context = Context().apply {

            /*----------------------MainPage----------------------*/
            setVariable("reportName", inspectionSite.get().reportName)
            setVariable("conductedAt", inspectionSite.get().conductedAt)
            setVariable("certificateNo", "STATIC")
            setVariable("inspectionDate", inspectionSite.get().inspectionDate)
            setVariable("siteImage", inspectionSite.get().imageUrl)
            setVariable("conductedBy", inspectionSite.get().conductedBy.name)


            /*----------------------AcknowledgementPage----------------------*/
            setVariable("items", acknowledgmentPersons)


            /*----------------------PreliminaryObservation----------------------*/
            setVariable("ruralArea", getYesNo(preObservation.ruralArea))
            setVariable("urbanArea", getYesNo(preObservation.urbanArea))
            setVariable("coastalArea", getYesNo(preObservation.coastalArea))
            setVariable("industrialPollutedArea", getYesNo(preObservation.industrialPollutedArea))
            setVariable("chemicalExposed", preObservation.chemicalsExposed)
            setVariable("avgHumidity", preObservation.averageHumidity)
            setVariable("salineAtmosphere", preObservation.salineAtmosphere)

        }

        // Render the HTML content using Thymeleaf
        val htmlContent = templateEngine.process("pdf_template", context)

        // Convert the rendered HTML to PDF
        val pdfBytes = pdfService.convertHtmlToPdfBytes(htmlContent)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes)
    }

    fun getYesNo(value: Boolean): String {
        return if (value) return "Yes" else "No"
    }
}
