package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.ToolTipInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ToolTipInfoRepository : JpaRepository<ToolTipInfo, Long> {


    @Query("select t from ToolTipInfo t where t.category.id = ?1")
    fun findAllByCategoryId(id: Long): List<ToolTipInfo>

}