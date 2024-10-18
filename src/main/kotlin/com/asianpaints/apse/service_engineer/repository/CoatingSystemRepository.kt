package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.CoatingSystem
import org.springframework.data.jpa.repository.JpaRepository

interface CoatingSystemRepository : JpaRepository<CoatingSystem, Long> {
}