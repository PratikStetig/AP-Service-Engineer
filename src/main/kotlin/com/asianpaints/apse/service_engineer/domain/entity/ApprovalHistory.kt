package com.asianpaints.apse.service_engineer.domain.entity


import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "APPROVAL_HISTORY")
data class ApprovalHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_site_id")
    val inspectionSite: InspectionSite,
    @Column(nullable = false)
    val approverId: Long,
    @Column(nullable = false)
    @CreationTimestamp
    val actionDate: LocalDateTime = LocalDateTime.now(),
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private val action: InspectionSiteStatus,
    @Column(name = "remarks", nullable = false, columnDefinition = "TEXT")
    val remarks: String,
)