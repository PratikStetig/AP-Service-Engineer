package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.dto.SiteAreaImageDto
import com.asianpaints.apse.service_engineer.service.SiteAreaImageService
import org.hibernate.exception.GenericJDBCException
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaSystemException
import org.springframework.web.bind.annotation.*
import java.sql.SQLException

@RestController
@RequestMapping("/api/v1/site-area-images")
class SiteAreaImageController(private val siteAreaImageService: SiteAreaImageService) {

    @PostMapping
    fun uploadImage(@RequestBody dto: SiteAreaImageDto): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(siteAreaImageService.createSiteAreaImage(dto))
        } catch (e: SQLException) {
            ResponseEntity.internalServerError().body<Any>(e.message)
        } catch (e: GenericJDBCException) {
            ResponseEntity.internalServerError().body<Any>(e.message)
        } catch (e: JpaSystemException) {
            ResponseEntity.internalServerError().body<Any>(e.cause?.cause?.message)
        } catch (e: Exception) {
            if (e.cause is JpaSystemException) {
                val cause: Throwable? = e.cause
                val message = if (cause is SQLException) cause.message else e.message
                ResponseEntity.internalServerError().body<Any>(message)
            }
            ResponseEntity.internalServerError().body<Any>(e.message)
        }
    }

    private fun handleGenericJDBCException(e: GenericJDBCException): ResponseEntity<Any> {
        val rootCause = e.cause
        return if (rootCause is SQLException) {
            // Handle SQLException from the trigger here
            when (rootCause.message) {
                "Cannot insert more than 3 images for an area" -> {
                    ResponseEntity.badRequest().body("Error: Cannot insert more than 3 images for this area.")
                }

                else -> {
                    ResponseEntity.internalServerError().body("Database Error: ${rootCause.message}")
                }
            }
        } else {
            // Handle cases where the root cause is not SQLException
            ResponseEntity.internalServerError().body("Database Error: ${e.message}")
        }
    }

    @GetMapping("/{siteAreaId}")
    fun getImagesByArea(@PathVariable siteAreaId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(siteAreaImageService.getSiteAreaImage(siteAreaId))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(e.message)
        }
    }


    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            siteAreaImageService.deleteImage(id)
            ResponseEntity.noContent().build() // Return 204 No Content on successful deletion
        } catch (e: SQLException) {
            ResponseEntity.internalServerError().body<Any>(e.message)
        } catch (e: GenericJDBCException) {
            ResponseEntity.internalServerError().body<Any>(e.message)
        } catch (e: JpaSystemException) {
            ResponseEntity.internalServerError().body<Any>(e.cause?.cause?.message)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body<Any>(e.message)
        }
    }
}
