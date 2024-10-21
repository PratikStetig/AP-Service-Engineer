package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.domain.entity.DropdownCategory
import com.asianpaints.apse.service_engineer.dto.CoatingSystemDto
import com.asianpaints.apse.service_engineer.dto.DropdownValueProjection
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound
import com.asianpaints.apse.service_engineer.exception.ProductLimitException
import com.asianpaints.apse.service_engineer.service.DropdownService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.xml.ws.Response


@RestController
@RequestMapping("api/v1/dropdown")
class DropdownController(private val dropdownService: DropdownService) {

    @GetMapping("/{categoryName}")
    fun getDropdownValues(@PathVariable categoryName: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(dropdownService.getDropdownValuesByCategoryName(categoryName))
        } catch (e: Exception) {
            ResponseEntity.internalServerError().body(e.message)
        }
    }
}