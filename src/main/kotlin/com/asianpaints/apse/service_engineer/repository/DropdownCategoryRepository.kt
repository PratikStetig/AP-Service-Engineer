package com.asianpaints.apse.service_engineer.repository

import com.asianpaints.apse.service_engineer.domain.entity.DropdownCategory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DropdownCategoryRepository : JpaRepository<DropdownCategory, Long> {
    fun findByName(name: String): DropdownCategory?
}
