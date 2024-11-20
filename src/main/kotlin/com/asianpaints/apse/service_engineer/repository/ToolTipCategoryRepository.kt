package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.ToolTipCategory
import org.springframework.data.jpa.repository.JpaRepository

interface ToolTipCategoryRepository : JpaRepository<ToolTipCategory, Long> {
    fun findByName(name: String): ToolTipCategory
}