package com.asianpaints.apse.service_engineer.controller

import com.asianpaints.apse.service_engineer.service.RustingInfoService
import com.asianpaints.apse.service_engineer.domain.entity.ToolTipCategory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tool-tip")
class RustingInfoController(
    private val service: RustingInfoService
) {

    @GetMapping
    fun getAll(): List<ToolTipCategory> = service.getAllCategory()

    @GetMapping("/{categoryName}")
    fun getById(@PathVariable categoryName: String): Any = service.getAllByCategory(categoryName)

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun create(@RequestBody dto: RustingInfoResponseDTO): RustingInfoResponseDTO = service.create(dto)

//    @PutMapping("/{id}")
//    fun update(@PathVariable id: Long, @RequestBody dto: RustingInfoResponseDTO): RustingInfoResponseDTO =
//        service.update(id, dto)
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun delete(@PathVariable id: Long) = service.delete(id)
}
