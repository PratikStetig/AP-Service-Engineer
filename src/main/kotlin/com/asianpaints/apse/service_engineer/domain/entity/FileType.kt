package com.asianpaints.apse.service_engineer.domain.entity

import javax.persistence.*

@Entity
@Table(name = "FILE_TYPE")
data class FileType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val name: String,

    val description: String? = null
)
