package com.asianpaints.apse.service_engineer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SiteMoreInformationDto {
    private Long id;
    private Long corrosivityEnvironmentId;
    private String possibleSurfacePreparation;
    private String recoatingInterval;
    private String serviceLife;
    private String shade;
    private String aesthetic;
    private String existingPaintingSystem;
    private String dftExistingSystem;
    private String remarks;
}
