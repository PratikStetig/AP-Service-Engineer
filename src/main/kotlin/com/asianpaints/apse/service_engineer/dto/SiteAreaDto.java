package com.asianpaints.apse.service_engineer.dto;

import com.asianpaints.apse.service_engineer.domain.entity.CorrosionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteAreaDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;
    public Long inspectionSiteId;
    public String area;
    public String coatingCondition;
    public CorrosionType corrosionType;
    public Integer rating;
}
