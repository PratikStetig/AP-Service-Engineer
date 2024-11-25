package com.asianpaints.apse.service_engineer.dto;


import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSiteReportVersionsResponse {
    @ReadOnlyProperty
    public Long id;
    private String pdfUrl;
    private Integer versionNumber;
    private LocalDateTime createdAt;
    public Long inspectionSiteId;
    public Boolean deleted = false;
}
