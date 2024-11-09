package com.asianpaints.apse.service_engineer.service

import com.itextpdf.html2pdf.HtmlConverter
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

@Service
class PdfService {

    fun convertHtmlToPdf(htmlContent: String, outputPath: String) {
        FileOutputStream(outputPath).use { outputStream ->
            HtmlConverter.convertToPdf(htmlContent.byteInputStream(), outputStream)
        }
    }

    fun convertHtmlToPdfBytes(htmlContent: String): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(htmlContent.byteInputStream(), byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}
