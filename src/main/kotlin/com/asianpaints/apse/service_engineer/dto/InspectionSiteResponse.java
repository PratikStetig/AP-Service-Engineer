package com.asianpaints.apse.service_engineer.dto;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSiteResponse {
    private Long id;
    private String reportName;
    private String conductedAt;
    private Long userId;
    private String siteId;
    private String state;
    private String city;
    private String imageUrl;
    private LocalDateTime createdOn;
    private InspectionSiteStatus status;
}
