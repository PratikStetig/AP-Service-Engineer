package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.ProductMaster
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductMaster, Long> {
}