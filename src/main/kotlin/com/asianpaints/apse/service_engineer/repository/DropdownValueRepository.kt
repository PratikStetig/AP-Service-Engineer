package com.asianpaints.apse.service_engineer.repository

import com.asianpaints.apse.service_engineer.domain.entity.DropdownValues
import com.asianpaints.apse.service_engineer.dto.DropdownValueProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.PathVariable


@Repository
interface DropdownValueRepository : JpaRepository<DropdownValues, Long> {

    @Query("""
        SELECT dv.id, dv.value
        FROM dropdown_values dv
        JOIN dropdown_category dc ON dv.category_id = dc.id
        WHERE dc.name = :category AND dv.is_active = true
        ORDER BY dv.display_order
    """, nativeQuery = true)
    fun getAllCategoryAndIsActiveTrue(@Param("category") category: String): List<DropdownValueProjection>

}
