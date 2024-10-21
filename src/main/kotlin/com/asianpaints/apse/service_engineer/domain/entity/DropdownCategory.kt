package com.asianpaints.apse.service_engineer.domain.entity


import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "DROPDOWN_CATEGORY")
data class DropdownCategory(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true)
    val name: String,

    val description: String? = null
)


/*CREATE TABLE dropdown_values (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
value VARCHAR(255) NOT NULL,
category VARCHAR(255) NOT NULL,
display_order INT DEFAULT 0,
is_active BOOLEAN DEFAULT TRUE
);*/

