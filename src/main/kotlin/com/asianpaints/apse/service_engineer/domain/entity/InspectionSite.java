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
    public Long id;
    @Column(name = "report_name")
    public String reportName;
    @Column(name = "conducted_at")
    public String conductedAt;
    @ManyToOne()
    @JoinColumn(name = "conducted_by")
    public ApUser conductedBy;
    @Column(name = "site_id")
    public String siteId;
    public String state;
    public String city;
    @Column(name = "image_url")
    public String imageUrl;
    @Column(name = "inspection_date")
    public LocalDate inspectionDate;
    @Column(name = "created_on")
    public LocalDateTime createdOn;
    @Enumerated(EnumType.STRING)
    public InspectionSiteStatus status;
    @Column(name = "deleted", nullable = false)
    public Boolean deleted = false;
    @ManyToOne()
    @JoinColumn(name = "zone_id")
    public Zone zone;
}
