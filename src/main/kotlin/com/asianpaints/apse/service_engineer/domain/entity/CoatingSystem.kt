package com.asianpaints.apse.service_engineer.domain.entity



import javax.persistence.*

@Entity
@Table(name = "COATING_SYSTEM")
data class CoatingSystem(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coating_system_id")
    val id: Long = 0,

    @Column(name = "coating_system_name")
    var coatingSystemName: String,

    @Column(name = "corrosivity_level")
    var corrosivityLevel: String,

    @Column(name = "type_of_structures")
    var typeOfStructures: String,

    @Column(name = "surface_preparation", length = 1000)
    var surfacePreparation: String,

    @Column(name = "srfa_bare_metal", length = 1000)
    var srfaBareMetal: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_site_id")
    var inspectionSiteId: InspectionSite,

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "COATING_SYSTEM_PRODUCT",
//        joinColumns = [JoinColumn(name = "coating_system_id")],
//        inverseJoinColumns = [JoinColumn(name = "product_id")]
//    )
//    @OnDelete(action = OnDeleteAction.CASCADE)  // Cascade delete for related Products
//    var products: MutableSet<ProductMaster> = mutableSetOf(),

    @OneToMany(
        fetch = FetchType.EAGER,
        mappedBy = "coatingSystem",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    ) var productDetails: MutableSet<CoatingSystemProductDetails> = mutableSetOf(),


    @ManyToMany(
        fetch = FetchType.EAGER,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH]
    ) @JoinTable(
        name = "AREA_COATING_SYSTEM_MAPPING",
        joinColumns = [JoinColumn(name = "coating_system_id")],
        inverseJoinColumns = [JoinColumn(name = "area_id")]
    ) var siteAreas: MutableSet<SiteArea> = mutableSetOf()
) {

    fun addProduct(product: ProductMaster, wftMin: Int, wftMax: Int, dft: Double, layerOrder: Int, paint: Boolean, spray: Boolean, applicationCategory: String) {
        val details = CoatingSystemProductDetails(
            coatingSystem = this,
            product = product,
            wftMin = wftMin,
            wftMax = wftMax,
            dft = dft,
            paint = paint,
            spray = spray,
            layerOrder = layerOrder,
            applicationCategory = applicationCategory
        )
        productDetails.add(details)
    }

    fun removeProduct(product: ProductMaster) {
        productDetails.removeIf { it.product == product }
    }

    fun getOrderedProducts(): List<CoatingSystemProductDetails> {
        return productDetails.sortedBy { it.layerOrder }
    }

    override fun equals(other: Any?): Boolean = (other is CoatingSystem) && other.id == this.id
    override fun hashCode(): Int = id.hashCode()
}

