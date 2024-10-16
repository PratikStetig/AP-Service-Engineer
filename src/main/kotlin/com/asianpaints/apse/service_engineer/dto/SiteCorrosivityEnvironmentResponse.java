package com.asianpaints.apse.service_engineer.dto;

import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SiteCorrosivityEnvironmentResponse {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private Long inspectionSiteId;
    private String rustingDegree;
    private Integer crackingSize;
    private Integer crackingQty;
    private String pattern;
    private Integer blisteringSize;
    private Integer blisteringQty;
    private Integer flakingSize;
    private Integer flakingQty;
    private String color;
    private String overallAppearance;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private Set<SiteAreaDto> siteAreas;
}