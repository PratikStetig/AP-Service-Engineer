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
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import lombok.NoArgsConstructor
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
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
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.transaction.Transactional


@Service
@NoArgsConstructor
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
    private val inspectionSiteReportVersionsRepository: InspectionSiteReportVersionsRepository,
    private val acknowledgmentTemplateService: AcknowledgmentTemplateService
) {

    private val logger = LoggerFactory.getLogger(PdfGenerationService::class.java)
    private val formatter = DateTimeFormatter.ofPattern("dd-MMM-yy")
    private lateinit var resourceLoader: ResourceLoader

    @Throws(IOException::class)
    fun loadTemplate(templateName: String): String {
        val resource: Resource = resourceLoader.getResource("file:/path/to/templates/$templateName")
        return String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8)
    }

    fun getPdfPreview(@PathVariable inspectionId: Long, model: Model): String {
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
        val templateContent = loadTemplate("pdf_template_preview.html")
        val htmlContent: String = templateEngine.process(templateContent, context)

//        val htmlContent = templateEngine.process("pdf_template_preview_bkp_01.html", context)

        return htmlContent
    }


    fun previewPdfBytes(inspectionId: Long): ResponseEntity<ByteArray> {

        try {
            val startTime = System.currentTimeMillis()
            logger.info("Starting PDF generation for inspectionId $inspectionId")

            val inspectionSiteStart = System.currentTimeMillis()
            val inspectionSite = inspectionSiteRepository.findById(inspectionId)
            logger.info("Step 1: Retrieved inspection site. Time taken: ${calculateTimeTaken(inspectionSiteStart)}")

            val acknowledgmentInfoStart = System.currentTimeMillis()
            val acknowledgmentInfo = acknowledgmentRepository.findByInspectionSiteId(inspectionId)
            val activeAcknowledgmentTemplate = acknowledgmentTemplateService.getActiveTemplate()
            val processedAckContent = activeAcknowledgmentTemplate?.templateContent?.trimIndent()
                ?.replace("#SITE_NAME#", "<strong>${inspectionSite.get().conductedAt}</strong>")
                ?.replace("#INSPECTION_DATE#", "<strong>${formatter.format(inspectionSite.get().inspectionDate)}</strong>")
            logger.info("Step 2: Retrieved acknowledgment info. Time taken: ${calculateTimeTaken(acknowledgmentInfoStart)}")

            val preObservationStart = System.currentTimeMillis()
            val preObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionId).get()
            logger.info("Step 3: Retrieved preliminary observation. Time taken: ${calculateTimeTaken(preObservationStart)}")

            val areasStart = System.currentTimeMillis()
            val areas = siteAreaRepository.findByInspectionSiteId(inspectionId).toList()
            logger.info("Step 4: Retrieved site areas. Time taken: ${calculateTimeTaken(areasStart)}")

            val corrosivityEnvironmentsStart = System.currentTimeMillis()
            val siteCorrosivityEnvironments = siteCorrosivityEnvironmentRepository.findByInspectionSiteId(inspectionId)
            val listOfAreaDetails = siteCorrosivityEnvironments.map { siteCorrosivityEnvironmentMapper.toDto(it) }
            logger.info("Step 5: Retrieved corrosivity environments. Time taken: ${calculateTimeTaken(corrosivityEnvironmentsStart)}")

            val coatingSystemStart = System.currentTimeMillis()
            val coatingSystem = coatingSystemRepository.getCoatingSystemByInspectionId(inspectionId)
            val coatingSystemResponse = coatingSystem.map { CoatingSystemMapper.toDto(it) }
            logger.info("Step 6: Retrieved coating system. Time taken: ${calculateTimeTaken(coatingSystemStart)}")

            val productDataSheetsStart = System.currentTimeMillis()
            val productDataSheets = coatingSystem.flatMap { coating -> coating.productDetails.map { it.product.productSheetLink } }.distinct()
            logger.info("Step 7: Retrieved product data sheets. Time taken: ${calculateTimeTaken(productDataSheetsStart)}")

            val contextStart = System.currentTimeMillis()
            val pageCounterUtil = PageCounterUtil()
            val context = Context().apply {


                /*----------------------MainPage----------------------*/
                setVariable("reportName", inspectionSite.get().reportName)
                setVariable("conductedAt", inspectionSite.get().conductedAt)
                setVariable("inspectionDate", formatter.format(inspectionSite.get().inspectionDate))
                setVariable("siteImage", inspectionSite.get().imageUrl)
                setVariable("conductedBy", inspectionSite.get().conductedBy.name)
                setVariable("designation", inspectionSite.get().conductedBy.userDesignation.designation)
                pageCounterUtil.addToTotal(1)


                /*----------------------AcknowledgementPage----------------------*/
                setVariable("processedAckContent", processedAckContent)
                setVariable("ackPersons", acknowledgmentInfo)
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

                /*----------------------Add Product sheets----------------------*/
                setVariable("productSheets", productDataSheets)
                pageCounterUtil.addToTotal(productDataSheets.size)

                /*----------------------List Of Areas----------------------*/
                setVariable("siteAreas", areas)
                pageCounterUtil.addToTotal(areas.size)

                /*----------------------Corrosive Environment----------------------*/
                setVariable("areaDetails", listOfAreaDetails)
                pageCounterUtil.addToTotal(listOfAreaDetails.size)

                setVariable("pageCounterUtil", pageCounterUtil)

            }
            logger.info("Step 8: Prepared Thymeleaf context. Time taken: ${calculateTimeTaken(contextStart)}")

            val htmlContentStart = System.currentTimeMillis()
            val htmlContent = templateEngine.process("pdf_template_preview.html", context)
            logger.info("Step 9: Rendered HTML content. Time taken: ${calculateTimeTaken(htmlContentStart)}")

            val pdfBytesStart = System.currentTimeMillis()
            val pdfBytes = convertHtmlToPdfBytes(htmlContent)
//            val pdfBytes = generatePdf(htmlContent, context)
            val compressedPdfBytes = compressPdf(pdfBytes)
            logger.info("Step 10: Converted HTML to PDF. Time taken: ${calculateTimeTaken(pdfBytesStart)}")

            logger.info("PDF generation completed for inspectionId $inspectionId. Total time taken: ${calculateTimeTaken(startTime)}")

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(compressedPdfBytes)
        } catch (ex: Exception) {
            logger.error("Failed to generate PDF for inspectionId $inspectionId", ex)
            saveFailureLog(inspectionId, ex.stackTraceToString() ?: "Unknown error")
            return ResponseEntity.status(500).body("Failed to generate PDF".toByteArray())
        }
    }

    private fun calculateTimeTaken(startTime: Long): String {
        val elapsedMillis = System.currentTimeMillis() - startTime
        val minutes = (elapsedMillis / 1000) / 60
        val seconds = (elapsedMillis / 1000) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }


    fun fixHtmlContent(content: String?): String {
        if (content.isNullOrBlank()) {
            throw IllegalArgumentException("HTML content cannot be null or empty")
        }
        val document = Jsoup.parse(content) // Parse the HTML
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml) // Force XHTML syntax
        return document.html() // Return the cleaned XHTML
    }

    fun wrapInXhtml(content: String): String {
        return content.trimIndent()
    }


    fun prepareHtmlForPdf(content: String?): String {
        val fixedContent = fixHtmlContent(content)
        return wrapInXhtml(fixedContent)
    }

    fun generatePdf(content: String?, context: Context?): ByteArray {
        val startTime = System.currentTimeMillis() // Start timing
        logger.info("Starting PDF generation process...")

        if (content.isNullOrBlank()) {
            logger.error("HTML content cannot be null or empty")
            throw IllegalArgumentException("HTML content cannot be null or empty")
        }

        val cleanHtml = prepareHtmlForPdf(content)
        try {
            ByteArrayOutputStream().use { baos ->
                val builder = PdfRendererBuilder()
                builder.useFastMode()
                builder.withHtmlContent(cleanHtml, null)
                builder.toStream(baos)
                builder.run()

                val totalTime = calculateTimeTaken(startTime) // Calculate total time
                logger.info("PDF generation completed. Total time taken: $totalTime")

                return baos.toByteArray()
            }
        } catch (e: Exception) {
            logger.error("Error while generating PDF", e)
            throw RuntimeException("Error while generating PDF", e)
        }
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
                    setVariable("inspectionDate", formatter.format(inspectionSite.get().inspectionDate))
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
//                val pdfBytes = generatePdf(htmlContent, context)

                saveInspectionPdf(inspectionId, pdfBytes)

                deferredResult.setResult(
                    ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=generated.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes)
                )
            } catch (ex: Exception) {
                // Log the error and store it in the database
                logger.error("Failed to generate PDF for inspectionId $inspectionId", ex)
                saveFailureLog(inspectionId, ex.stackTraceToString())

                deferredResult.setErrorResult(ResponseEntity.status(500).body("Failed to generate PDF".toByteArray()))
            }
        }
        return deferredResult
    }


    @Throws(java.lang.Exception::class)
    fun compressPdf(pdfBytes: ByteArray?): ByteArray {
        // Load the input PDF
        val document = PDDocument.load(ByteArrayInputStream(pdfBytes))
        for (page in document.pages) {
            val resources = page.resources
            for (xObjectName in resources.xObjectNames) {
                val xObject = resources.getXObject(xObjectName)
                if (xObject is PDImageXObject) {

                    // Convert image to RGB if necessary
                    val bufferedImage = ensureRGB(xObject.image)

                    // Downscale the image to reduce resolution
                    val downscaledImage = downscaleImage(bufferedImage, 0.5) // Scale factor: 50%

                    // Compress the image with a reduced quality
                    val imageOutputStream = ByteArrayOutputStream()
                    val jpegWriter = ImageIO.getImageWritersByFormatName("JPEG").next()
                    val jpegParams = jpegWriter.defaultWriteParam
                    jpegParams.compressionMode = javax.imageio.ImageWriteParam.MODE_EXPLICIT
                    jpegParams.compressionQuality = 0.7f // Set compression quality (0.7 = 70%)

                    val jpegOutput = ImageIO.createImageOutputStream(imageOutputStream)
                    jpegWriter.output = jpegOutput
                    jpegWriter.write(null, IIOImage(downscaledImage, null, null), jpegParams)
                    jpegWriter.dispose()
                    jpegOutput.close()

                    // Replace the existing image in the PDF
                    val compressedImage = PDImageXObject.createFromByteArray(
                        document,
                        imageOutputStream.toByteArray(),
                        "compressed_image"
                    )
                    resources.put(xObjectName, compressedImage)
                }
            }
        }

        // Save the compressed PDF to a ByteArrayOutputStream
        val compressedOutput = ByteArrayOutputStream()
        document.save(compressedOutput)
        document.close()
        return compressedOutput.toByteArray()
    }

    // Utility method to ensure the image is in RGB format
    private fun ensureRGB(image: BufferedImage): BufferedImage {
        if (image.type == BufferedImage.TYPE_INT_RGB) {
            return image // Image is already in RGB format
        }
        val rgbImage = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_RGB)
        val g = rgbImage.createGraphics()
        g.color = Color.WHITE
        g.fillRect(0, 0, image.width, image.height)
        g.drawImage(image, 0, 0, null)
        g.dispose()
        return rgbImage
    }

    // Utility method to downscale an image
    private fun downscaleImage(image: BufferedImage, scaleFactor: Double): BufferedImage {
        val width = (image.width * scaleFactor).toInt()
        val height = (image.height * scaleFactor).toInt()
        val scaledImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val g = scaledImage.createGraphics()
        g.drawImage(image, 0, 0, width, height, null)
        g.dispose()
        return scaledImage
    }


    private fun getYesNo(value: Boolean?): String = if (value == true) "Yes" else "No"

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
        val startTime = System.currentTimeMillis()
        return try {
            val imageBytes = URL(imageUrl).readBytes()
            val base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes)
            logger.info("Encoded image to Base64. URL: $imageUrl, Time taken: ${calculateTimeTaken(startTime)}")
            base64Image
        } catch (e: Exception) {
            logger.warn("Failed to encode image to Base64. URL: $imageUrl, Time taken: ${calculateTimeTaken(startTime)}", e)
            imageUrl // return the original URL if fetching fails
        }
    }

    fun convertImagesToBase64(htmlContent: String): String {
        val startTime = System.currentTimeMillis()
        val document: Document = Jsoup.parse(htmlContent)
        document.select("img[src]").forEach { img ->
            val src = img.attr("src")
            if (src.startsWith("http") || src.startsWith("https")) {
                val base64Image = encodeImageToBase64(src)
                img.attr("src", base64Image)
            }
        }
        logger.info("Converted images to Base64. Time taken: ${calculateTimeTaken(startTime)}")
        return document.html()
    }

    fun convertHtmlToPdfBytes(htmlContent: String): ByteArray {
        val startTime = System.currentTimeMillis()
        val processedHtmlStart = System.currentTimeMillis()
//        val processedHtml = convertImagesToBase64(htmlContent) // Convert all images to Base64
        logger.info("Processed HTML for PDF conversion. Time taken: ${calculateTimeTaken(processedHtmlStart)}")

        val pdfConversionStart = System.currentTimeMillis()
        val byteArrayOutputStream = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(htmlContent.byteInputStream(), byteArrayOutputStream)
        val pdfBytes = byteArrayOutputStream.toByteArray()
        logger.info("Converted HTML to PDF bytes. Time taken: ${calculateTimeTaken(pdfConversionStart)}")

        logger.info("Total time taken for PDF generation (including image conversion): ${calculateTimeTaken(startTime)}")
        return pdfBytes
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


