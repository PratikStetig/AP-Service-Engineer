package com.asianpaints.apse.service_engineer.domain.entity


import javax.persistence.*

@Entity
@Table(name = "COATING_SYSTEM")
data class CoatingSystem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coating_system_id")
    val id: Long = 0,

    @Column(name = "coating_system_name", columnDefinition = "TEXT")
    val coatingSystemName: String,

    @Column(name = "corrosivity_level")
    val corrosivityLevel: String,  // Drop-down should be implemented on the frontend

    @Column(name = "type_of_structures", columnDefinition = "TEXT")
    val typeOfStructures: String,

    @Column(name = "surface_preparation", columnDefinition = "TEXT")
    val surfacePreparation: String,

    @Column(name = "srfa_bare_metal", columnDefinition = "TEXT")
    val srfaBareMetal: String,

    @Column(name = "paint")
    val paint: Boolean,

    @Column(name = "spary")
    val spray: Boolean,

    @Column(name = "WFT")
    val wft: Double,

    @Column(name = "DFT")
    val dft: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_report_id")
    val inspectionReport: InspectionSite,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "COATING_SYSTEM_PRODUCT",
        joinColumns = [JoinColumn(name = "coating_system_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: List<ProductMaster> = mutableListOf()
)

