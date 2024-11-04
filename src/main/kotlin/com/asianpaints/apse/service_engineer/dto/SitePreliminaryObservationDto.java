package com.asianpaints.apse.service_engineer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SitePreliminaryObservationDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private Long inspectionSiteId;
    private boolean ruralArea;
    private boolean urbanArea;
    private boolean coastalArea;
    private boolean industrialPollutedArea;
    private String chemicalsExposed;
    private boolean salineAtmosphere;
    private double averageHumidity;
    private String description;

}
