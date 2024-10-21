package com.asianpaints.apse.service_engineer.domain.entity


import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "DROPDOWN_VALUES")
data class DropdownValues(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "value")
    var value: String,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: DropdownCategory,

    @Column(name = "display_order", columnDefinition = "TEXT")
    var displayOrder: Int = 0,

    @Column(name = "is_active")
    var isActive: Boolean = true
)


/*CREATE TABLE dropdown_values (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
value VARCHAR(255) NOT NULL,
category VARCHAR(255) NOT NULL,
display_order INT DEFAULT 0,
is_active BOOLEAN DEFAULT TRUE
);*/

