package com.asianpaints.apse.service_engineer.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INSPECTION_SITE")
public class InspectionSite {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "report_name")
    private String reportName;
    @Column(name = "conducted_at")
    private String conductedAt;
    @ManyToOne()
    @JoinColumn(name = "conducted_by")
    private ApUser conductedBy;
    @Column(name = "site_id")
    private String siteId;
    private String state;
    private String city;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "inspection_date")
    private LocalDate inspectionDate;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Enumerated(EnumType.STRING)
    private InspectionSiteStatus status;
}
