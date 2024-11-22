package com.asianpaints.apse.service_engineer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteAreaImageDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;
    public Long siteAreaId;
    public String imageUrl;
    public LocalDateTime uploadedAt;
}
