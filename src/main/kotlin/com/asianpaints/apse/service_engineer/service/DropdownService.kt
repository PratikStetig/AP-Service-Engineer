package com.asianpaints.apse.service_engineer.service

import com.asianpaints.apse.service_engineer.dto.DropdownValueProjection
import com.asianpaints.apse.service_engineer.repository.DropdownCategoryRepository
import com.asianpaints.apse.service_engineer.repository.DropdownValueRepository
import org.springframework.stereotype.Service

@Service
class DropdownService(
    private val dropdownValueRepository: DropdownValueRepository,
    private val dropdownCategoryRepository: DropdownCategoryRepository
) {

    fun getDropdownValuesByCategoryName(categoryName: String): List<DropdownValueProjection> {
        val category = dropdownCategoryRepository.findByName(categoryName) ?: throw NoSuchElementException("Category not found: $categoryName")
        return dropdownValueRepository.getAllCategoryAndIsActiveTrue(categoryName);
    }
}
