package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.ProductLimitException
import com.asianpaints.apse.service_engineer.service.CoatingSystemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/coating")
class CoatingSystemController(private val coatingSystemService: CoatingSystemService) {


    @GetMapping("/inspection-site/{inspectionSiteId}/coating-system/")
    fun getAllInspectionCoatingSystem(@PathVariable inspectionSiteId: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(coatingSystemService.getAllCoatingSystemByInspectionId(inspectionSiteId))
        } catch (ex: InspectionSiteNotFound) {
            ResponseEntity.badRequest().body(ex.message)
        } catch (e: java.lang.Exception) {
            ResponseEntity.internalServerError().body(e.message)
        }
    }

    @PostMapping("/coating-system")
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
}
