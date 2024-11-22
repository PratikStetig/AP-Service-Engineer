package com.asianpaints.apse.service_engineer.dto;

import com.asianpaints.apse.service_engineer.domain.entity.CorrosionType;
import com.asianpaints.apse.service_engineer.domain.entity.SiteAreaImages;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteAreaDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;
    public String area;
    public Long inspectionSiteId;
    public String coatingCondition;
    public CorrosionType corrosionType;
    public List<SiteAreaImageDto> images;
    public Integer rating;
}
