package com.asianpaints.apse.service_engineer.domain.entity;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionSiteFilter {
    private Long conductedBy;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String siteId;
    private InspectionSiteStatus status;
    private String reportName;
    private Boolean admin = false;
    private String roleName;

    public InspectionSiteFilter(Long conductedBy, LocalDate fromDate, LocalDate toDate, String siteId, InspectionSiteStatus status, String reportName) {
        this.conductedBy = conductedBy;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.siteId = siteId;
        this.status = status;
        this.reportName = reportName;
    }
}