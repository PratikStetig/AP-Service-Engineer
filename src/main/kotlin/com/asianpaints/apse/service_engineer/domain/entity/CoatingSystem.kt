package com.asianpaints.apse.service_engineer.domain.entity


import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "COATING_SYSTEM")
data class CoatingSystem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coating_system_id")
    val id: Long = 0,

    @Column(name = "coating_system_name", columnDefinition = "TEXT")
    var coatingSystemName: String,

    @Column(name = "corrosivity_level")
    var corrosivityLevel: String,  // Drop-down should be implemented on the frontend

    @Column(name = "type_of_structures", columnDefinition = "TEXT")
    var typeOfStructures: String,

    @Column(name = "surface_preparation", columnDefinition = "TEXT")
    var surfacePreparation: String,

    @Column(name = "srfa_bare_metal", columnDefinition = "TEXT")
    var srfaBareMetal: String,

    @Column(name = "paint")
    var paint: Boolean,

    @Column(name = "spary")
    var spray: Boolean,

    @Column(name = "WFT")
    var wft: Double,

    @Column(name = "DFT")
    var dft: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_site_id")
    val inspectionSiteId: InspectionSite,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "COATING_SYSTEM_PRODUCT",
        joinColumns = [JoinColumn(name = "coating_system_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    @OnDelete(action = OnDeleteAction.CASCADE)  // Cascade delete for related Products
    var products: MutableSet<ProductMaster> = mutableSetOf(),

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    )
    @JoinTable(name = "AREA_COATING_SYSTEM_MAPPING", joinColumns = [JoinColumn(name = "coating_system_id")], inverseJoinColumns = [JoinColumn(name = "area_id")])
    val siteAreas: Set<SiteArea>
)

