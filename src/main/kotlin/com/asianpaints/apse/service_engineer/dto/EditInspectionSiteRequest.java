package com.asianpaints.apse.service_engineer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditInspectionSiteRequest {
    private Long id;
    private String reportName;
    private String conductedAt;
    private Long userId;
    private String siteId;
    private String state;
    private String city;
    private String imageUrl;
}
