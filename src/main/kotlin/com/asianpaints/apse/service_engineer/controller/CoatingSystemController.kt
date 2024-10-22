package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.dto.CoatingSystemResponse
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.ProductLimitException
import com.asianpaints.apse.service_engineer.service.CoatingSystemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("api/v1/coating-system")
class CoatingSystemController(private val coatingSystemService: CoatingSystemService) {


    @PostMapping
    fun createCoatingSystem(@RequestBody coatingSystem: CoatingSystemDto): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(coatingSystemService.addCoatingSystem(coatingSystem))
        } catch (ex: InspectionSiteNotFound) {
            ResponseEntity.badRequest().body(ex.message)
        } catch (ex: ProductLimitException) {
            ResponseEntity.badRequest().body(ex.message)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(e.message)
        }

    }


    @DeleteMapping("/{id}")
    fun deleteCoatingSystem(@PathVariable id: Long): ResponseEntity<String> {
        return try {
            coatingSystemService.deleteCoatingSystem(id)
            ResponseEntity.ok("Coating System deleted successfully")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coating System not found")
        }
    }

    @PutMapping("/{id}")
    fun updateCoatingSystem(@PathVariable id: Long, @RequestBody coatingSystemDto: CoatingSystemDto): ResponseEntity<CoatingSystemResponse> {
        return try {
            val updatedCoatingSystem = coatingSystemService.updateCoatingSystem(id, coatingSystemDto)
            ResponseEntity.ok(updatedCoatingSystem)
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @DeleteMapping("/{coatingSystemId}/product/{productId}")
    fun removeProductFromCoatingSystem(
        @PathVariable coatingSystemId: Long,
        @PathVariable productId: Long
    ): ResponseEntity<String> {
        return try {
            coatingSystemService.removeProductFromCoatingSystem(coatingSystemId, productId)
            ResponseEntity.ok("Product removed from Coating System successfully")
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coating System or Product not found")
        }
    }


    @PostMapping("/{coatingSystemId}/product/{productId}")
    fun addProductToCoatingSystem(
        @PathVariable coatingSystemId: Long,
        @PathVariable productId: Long
    ): ResponseEntity<String> {
        return try {
            coatingSystemService.addProductToCoatingSystem(coatingSystemId, productId)
            ResponseEntity.ok("Product added to Coating System successfully")
        } catch (e: EntityNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Coating System or Product not found")
        }
    }

}
