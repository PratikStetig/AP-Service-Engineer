package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.mapper.CoatingSystemMapper
import com.asianpaints.apse.service_engineer.mapper.SiteCorrosivityEnvironmentMapper
import com.asianpaints.apse.service_engineer.repository.*
import com.asianpaints.apse.service_engineer.service.PdfService
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
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine


@RestController
@RequestMapping("/pdf")
class PdfController @Autowired constructor(
    private val templateEngine: SpringTemplateEngine,
    private val pdfService: PdfService,
    private val inspectionSiteRepository: InspectionSiteRepository,
    private val acknowledgmentRepository: InspectionSiteAcknowledgmentRepository,
    private val sitePreliminaryObservationRepository: SitePreliminaryObservationRepository,
    private val coatingSystemRepository: CoatingSystemRepository,
    private val siteAreaRepository: SiteAreaRepository,
    private val siteCorrosivityEnvironmentRepository: SiteCorrosivityEnvironmentRepository,
    private val siteCorrosivityEnvironmentMapper: SiteCorrosivityEnvironmentMapper
) {

    @GetMapping("/preview/{inspectionId}")
    fun getPdfPreview(@PathVariable inspectionId: Long, model: Model): String {
        val inspectionSite = inspectionSiteRepository.findById(inspectionId)
        val acknowledgmentInfo = acknowledgmentRepository.findByInspectionSiteId(inspectionId)
        val acknowledgmentPersons = acknowledgmentInfo.map { "${it.personName} ${it.designation}" }.toList()
        val preObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionId).get()
        val coatingSystem = coatingSystemRepository.getCoatingSystemByInspectionId(inspectionId)
        val coatingSystemResponse = coatingSystem.map { CoatingSystemMapper.toDto(it) }

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


            /*----------------------Coating System Recommendation----------------------*/
            setVariable("coatingSystems", coatingSystemResponse)

        }

        // Render the HTML content using Thymeleaf
        val htmlContent = templateEngine.process("pdf_template_preview.html", context)

        return htmlContent
    }


    @GetMapping("/generate/{inspectionId}")
    fun generatePdf(@PathVariable inspectionId: Long, model: Model): ResponseEntity<ByteArray> {
        val inspectionSite = inspectionSiteRepository.findById(inspectionId)
        val acknowledgmentInfo = acknowledgmentRepository.findByInspectionSiteId(inspectionId)
        val acknowledgmentPersons = acknowledgmentInfo.map { "${it.personName} ${it.designation}" }.toList()
        val preObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionId).get()
        val areas = siteAreaRepository.findByInspectionSiteId(inspectionId).toList()
        val siteCorrosivityEnvironments = siteCorrosivityEnvironmentRepository.findByInspectionSiteId(inspectionId)
        val listOfAreaDetails = siteCorrosivityEnvironments.map { siteCorrosivityEnvironmentMapper.toDto(it) }
        val coatingSystem = coatingSystemRepository.getCoatingSystemByInspectionId(inspectionId)
        val coatingSystemResponse = coatingSystem.map { CoatingSystemMapper.toDto(it) }
        val productDataSheets = emptyList<String>()
        val pageCounterUtil = PageCounterUtil()

        val context = Context().apply {


            /*----------------------MainPage----------------------*/
            setVariable("reportName", inspectionSite.get().reportName)
            setVariable("conductedAt", inspectionSite.get().conductedAt)
            setVariable("certificateNo", "STATIC")
            setVariable("inspectionDate", inspectionSite.get().inspectionDate)
            setVariable("siteImage", inspectionSite.get().imageUrl)
            setVariable("conductedBy", inspectionSite.get().conductedBy.name)
            pageCounterUtil.addToTotal(1)


            /*----------------------AcknowledgementPage----------------------*/
            setVariable("items", acknowledgmentPersons)
            pageCounterUtil.addToTotal(1)


            /*----------------------TableOfContent----------------------*/
            val inspectionDetailsStartingPageNo = 9
            val coatingSystemStartPageNo = inspectionDetailsStartingPageNo + listOfAreaDetails.size
            val productDataSheetStartPage = coatingSystemStartPageNo + coatingSystem.size
            val generalPracticeForTheRecommendedCoatingSystem = productDataSheetStartPage + productDataSheets.size
            setVariable("coatingSystemStartPage", coatingSystemStartPageNo)
            setVariable("productDataSheetStartPage", productDataSheetStartPage)
            setVariable("generalPracticeForTheRecommendedCoatingSystem", generalPracticeForTheRecommendedCoatingSystem)
            pageCounterUtil.addToTotal(1)


            /*----------------------PreliminaryObservation----------------------*/
            setVariable("ruralArea", getYesNo(preObservation.ruralArea))
            setVariable("urbanArea", getYesNo(preObservation.urbanArea))
            setVariable("coastalArea", getYesNo(preObservation.coastalArea))
            setVariable("industrialPollutedArea", getYesNo(preObservation.industrialPollutedArea))
            setVariable("chemicalExposed", preObservation.chemicalsExposed)
            setVariable("avgHumidity", preObservation.averageHumidity)
            setVariable("salineAtmosphere", preObservation.salineAtmosphere)
            pageCounterUtil.addToTotal(1)

            /*----------------------Coating System Recommendation----------------------*/
            setVariable("coatingSystems", coatingSystemResponse)
            pageCounterUtil.addToTotal(coatingSystemResponse.size)

            /*----------------------List Of Areas----------------------*/
            setVariable("siteAreas", areas)
            pageCounterUtil.addToTotal(areas.size)

            /*----------------------Corrosive Environment----------------------*/
            setVariable("areaDetails", listOfAreaDetails)
            pageCounterUtil.addToTotal(listOfAreaDetails.size)

            setVariable("pageCounterUtil", pageCounterUtil)
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
