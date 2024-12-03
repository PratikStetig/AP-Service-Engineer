package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.client.FileUploadClient
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteReportVersions
import com.asianpaints.apse.service_engineer.domain.entity.PdfGenFailureLog
import com.asianpaints.apse.service_engineer.dto.AzureFileUploadResponse
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.mapper.CoatingSystemMapper
import com.asianpaints.apse.service_engineer.mapper.SiteCorrosivityEnvironmentMapper
import com.asianpaints.apse.service_engineer.repository.*
import com.asianpaints.apse.service_engineer.util.PageCounterUtil
import com.fasterxml.jackson.databind.ObjectMapper
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.styledxmlparser.jsoup.Jsoup
import com.itextpdf.styledxmlparser.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.task.TaskExecutor
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.context.request.async.DeferredResult
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.net.URL
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional


@Service
class PdfGenerationService @Autowired constructor(
    private val templateEngine: SpringTemplateEngine,
    private val inspectionSiteRepository: InspectionSiteRepository,
    private val acknowledgmentRepository: InspectionSiteAcknowledgmentRepository,
    private val sitePreliminaryObservationRepository: SitePreliminaryObservationRepository,
    private val coatingSystemRepository: CoatingSystemRepository,
    private val siteAreaRepository: SiteAreaRepository,
    private val siteCorrosivityEnvironmentRepository: SiteCorrosivityEnvironmentRepository,
    private val siteCorrosivityEnvironmentMapper: SiteCorrosivityEnvironmentMapper,
    private val pdfGenFailureLogRepository: PdfGenFailureLogRepository,
    private val fileUploadClient: FileUploadClient,
    private val taskExecutor: TaskExecutor,
    private val inspectionSiteReportVersionsRepository: InspectionSiteReportVersionsRepository
) {

    private val logger = LoggerFactory.getLogger(PdfGenerationService::class.java)


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


    @Async
    @Retryable(value = [Exception::class], maxAttempts = 3, backoff = Backoff(delay = 2000))
    @Transactional
    fun generatePdfAsync(inspectionId: Long): DeferredResult<ResponseEntity<ByteArray>> {
        val deferredResult = DeferredResult<ResponseEntity<ByteArray>>()
        taskExecutor.execute {
            try {
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
                    setVariable("certificateNo", "1231244")
                    setVariable("inspectionDate", inspectionSite.get().inspectionDate)
                    setVariable("siteImage", inspectionSite.get().imageUrl)
                    setVariable("conductedBy", inspectionSite.get().conductedBy.name)
                    setVariable("designation", inspectionSite.get().conductedBy.userDesignation.designation)
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
                val pdfBytes = convertHtmlToPdfBytes(htmlContent)

//                saveInspectionPdf(inspectionId, pdfBytes)

                deferredResult.setResult(
                    ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes)
                )
            } catch (ex: Exception) {
                // Log the error and store it in the database
                logger.error("Failed to generate PDF for inspectionId $inspectionId", ex)
                saveFailureLog(inspectionId, ex.message ?: "Unknown error")

                deferredResult.setErrorResult(ResponseEntity.status(500).body("Failed to generate PDF".toByteArray()))
            }
        }
        return deferredResult
    }

    private fun saveInspectionPdf(inspectionId: Long, pdfBytes: ByteArray) {
        val fileName = "inspectionReport_${inspectionId}_${System.currentTimeMillis()}.pdf"
        val uploadedPdf = fileUploadClient.addFile(fileName, pdfBytes)
        val mapper = ObjectMapper()
        val azureFileResponse: AzureFileUploadResponse = mapper.readValue(uploadedPdf.body, AzureFileUploadResponse::class.java)

        if (azureFileResponse.assetUrl != null) {
            createReportVersion(azureFileResponse.assetUrl, inspectionId)
        }
    }


    @Transactional
    fun createReportVersion(pdfUrl: String?, inspectionSiteId: Long?) {
        var versionNumber: Int? = inspectionSiteReportVersionsRepository.findLatestVersionNumberByInspectionSiteId(inspectionSiteId)
        if (versionNumber == null) versionNumber = 0
        val inspectionSite = inspectionSiteRepository.findById(inspectionSiteId!!).orElse(null)
        if (inspectionSite == null) {
            val errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionSiteId)
            throw InspectionSiteNotFound(errMsg)
        }
        val reportVersion = InspectionSiteReportVersions()
        reportVersion.pdfUrl = pdfUrl
        reportVersion.versionNumber = ++versionNumber
        reportVersion.createdAt = LocalDateTime.now()
        reportVersion.inspectionSiteId = inspectionSite
        reportVersion.deleted = false
        inspectionSiteReportVersionsRepository.save(reportVersion)
    }


    private fun saveFailureLog(inspectionId: Long, reason: String) {
        val failureLog = PdfGenFailureLog(
            inspectionId = inspectionId,
            reason = reason,
            timestamp = LocalDateTime.now()
        )
        pdfGenFailureLogRepository.save(failureLog)
    }


    fun convertHtmlToPdf(htmlContent: String, outputPath: String) {
        FileOutputStream(outputPath).use { outputStream ->
            HtmlConverter.convertToPdf(htmlContent.byteInputStream(), outputStream)
        }
    }


    fun encodeImageToBase64(imageUrl: String): String {
        return try {
            val imageBytes = URL(imageUrl).readBytes()
            "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes)
        } catch (e: Exception) {
            imageUrl // return the original URL if fetching fails
        }
    }

    fun convertImagesToBase64(htmlContent: String): String {
        val document: Document = Jsoup.parse(htmlContent)
        document.select("img[src]").forEach { img ->
            val src = img.attr("src")
            if (src.startsWith("http") || src.startsWith("https")) {
                val base64Image = encodeImageToBase64(src)
                img.attr("src", base64Image)
            }
        }
        return document.html()
    }

    fun convertHtmlToPdfBytes(htmlContent: String): ByteArray {
        val processedHtml = convertImagesToBase64(htmlContent) // Convert all images to Base64
        val byteArrayOutputStream = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(processedHtml.byteInputStream(), byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    fun getYesNo(value: Boolean): String {
        return if (value) return "Yes" else "No"
    }

}


//@GetMapping("/generate/{inspectionId}")
//fun generatePdf2(@PathVariable inspectionId: Long): ResponseEntity<ByteArray> {
//    val inspectionSite = inspectionSiteRepository.findById(inspectionId)
//    val acknowledgmentInfo = acknowledgmentRepository.findByInspectionSiteId(inspectionId)
//    val acknowledgmentPersons = acknowledgmentInfo.map { "${it.personName} ${it.designation}" }.toList()
//    val preObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionId).get()
//    val areas = siteAreaRepository.findByInspectionSiteId(inspectionId).toList()
//    val siteCorrosivityEnvironments = siteCorrosivityEnvironmentRepository.findByInspectionSiteId(inspectionId)
//    val listOfAreaDetails = siteCorrosivityEnvironments.map { siteCorrosivityEnvironmentMapper.toDto(it) }
//    val coatingSystem = coatingSystemRepository.getCoatingSystemByInspectionId(inspectionId)
//    val coatingSystemResponse = coatingSystem.map { CoatingSystemMapper.toDto(it) }
//    val productDataSheets = emptyList<String>()
//    val pageCounterUtil = PageCounterUtil()
//
//    val context = Context().apply {
//
//
//        /*----------------------MainPage----------------------*/
//        setVariable("reportName", inspectionSite.get().reportName)
//        setVariable("conductedAt", inspectionSite.get().conductedAt)
//        setVariable("certificateNo", "STATIC")
//        setVariable("inspectionDate", inspectionSite.get().inspectionDate)
//        setVariable("siteImage", inspectionSite.get().imageUrl)
//        setVariable("conductedBy", inspectionSite.get().conductedBy.name)
//        pageCounterUtil.addToTotal(1)
//
//
//        /*----------------------AcknowledgementPage----------------------*/
//        setVariable("items", acknowledgmentPersons)
//        pageCounterUtil.addToTotal(1)
//
//
//        /*----------------------TableOfContent----------------------*/
//        val inspectionDetailsStartingPageNo = 9
//        val coatingSystemStartPageNo = inspectionDetailsStartingPageNo + listOfAreaDetails.size
//        val productDataSheetStartPage = coatingSystemStartPageNo + coatingSystem.size
//        val generalPracticeForTheRecommendedCoatingSystem = productDataSheetStartPage + productDataSheets.size
//        setVariable("coatingSystemStartPage", coatingSystemStartPageNo)
//        setVariable("productDataSheetStartPage", productDataSheetStartPage)
//        setVariable("generalPracticeForTheRecommendedCoatingSystem", generalPracticeForTheRecommendedCoatingSystem)
//        pageCounterUtil.addToTotal(1)
//
//
//        /*----------------------PreliminaryObservation----------------------*/
//        setVariable("ruralArea", getYesNo(preObservation.ruralArea))
//        setVariable("urbanArea", getYesNo(preObservation.urbanArea))
//        setVariable("coastalArea", getYesNo(preObservation.coastalArea))
//        setVariable("industrialPollutedArea", getYesNo(preObservation.industrialPollutedArea))
//        setVariable("chemicalExposed", preObservation.chemicalsExposed)
//        setVariable("avgHumidity", preObservation.averageHumidity)
//        setVariable("salineAtmosphere", preObservation.salineAtmosphere)
//        pageCounterUtil.addToTotal(1)
//
//        /*----------------------Coating System Recommendation----------------------*/
//        setVariable("coatingSystems", coatingSystemResponse)
//        pageCounterUtil.addToTotal(coatingSystemResponse.size)
//
//        /*----------------------List Of Areas----------------------*/
//        setVariable("siteAreas", areas)
//        pageCounterUtil.addToTotal(areas.size)
//
//        /*----------------------Corrosive Environment----------------------*/
//        setVariable("areaDetails", listOfAreaDetails)
//        pageCounterUtil.addToTotal(listOfAreaDetails.size)
//
//        setVariable("pageCounterUtil", pageCounterUtil)
//    }
//
//    // Render the HTML content using Thymeleaf
//    val htmlContent = templateEngine.process("pdf_template", context)
//
//    // Convert the rendered HTML to PDF
//    val pdfBytes = convertHtmlToPdfBytes(htmlContent)
//
//    return ResponseEntity.ok()
//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
//        .contentType(MediaType.APPLICATION_PDF)
//        .body(pdfBytes)
//}


