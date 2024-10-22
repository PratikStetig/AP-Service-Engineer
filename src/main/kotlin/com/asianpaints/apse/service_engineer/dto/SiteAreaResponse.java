package com.asianpaints.apse.service_engineer.dto;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SiteAreaResponse {
    private Long id;
    private String area;
    private Long inspectionSiteId;
    private String coatingCondition;
    private String corrosionType;
    private Integer rating;
}
