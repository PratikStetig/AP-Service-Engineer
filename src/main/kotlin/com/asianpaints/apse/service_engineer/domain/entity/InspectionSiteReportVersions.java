package com.asianpaints.apse.service_engineer.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INSPECTION_SITE_VERSIONS")
public class InspectionSiteReportVersions {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "version_number")
    private Integer versionNumber;

    @Column(name = "create_at")
    private LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "inspection_site_id")
    public InspectionSite inspectionSiteId;

    @Column(name = "deleted", nullable = false)
    public Boolean deleted = false;
}
