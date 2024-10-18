package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.ProductLimitException
import com.asianpaints.apse.service_engineer.service.CoatingSystemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.xml.ws.Response

@RestController
@RequestMapping("api/v1/coating")
class CoatingSystemController(private val coatingSystemService: CoatingSystemService) {

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
