package com.asianpaints.apse.service_engineer.service

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.styledxmlparser.jsoup.Jsoup
import com.itextpdf.styledxmlparser.jsoup.nodes.Document
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.net.URL
import java.util.*

@Service
class PdfService {

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

//    fun convertHtmlToPdfBytes(htmlContent: String): ByteArray {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        val converterProperties = ConverterProperties().apply {
//            isImmediateFlush = false // useful to handle large documents
//            baseUri = "https://placehold.co" // Base URI to resolve relative URLs if needed
//        }
//        HtmlConverter.convertToPdf(htmlContent.byteInputStream(), byteArrayOutputStream, converterProperties)
//        return byteArrayOutputStream.toByteArray()
//    }

    fun convertHtmlToPdfBytes(htmlContent: String): ByteArray {
        val processedHtml = convertImagesToBase64(htmlContent) // Convert all images to Base64
        val byteArrayOutputStream = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(processedHtml.byteInputStream(), byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}
