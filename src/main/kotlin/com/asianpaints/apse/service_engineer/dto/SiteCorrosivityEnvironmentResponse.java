package com.asianpaints.apse.service_engineer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /*------------------ ADD MORE INFORMATION MERGE------------------------*/
    private String possibleSurfacePreparation;
    private String recoatingInterval;
    private String recoatingIntervalOther;
    private String serviceLife;
    private String shade;
    private String aesthetic;
    private String existingPaintingSystem;
    private String dftExistingSystem;
    private String remarks;
    private String affectedAreas;

    public String siteAreaNamesCSV() {
        return siteAreas.stream()
                .map(SiteAreaDto::getArea)
                .collect(Collectors.joining(", "));
    }

    public String siteAreaStructureTypesCSV() {
        return siteAreas.stream()
                .map(SiteAreaDto::getStructureType)
                .collect(Collectors.joining(", "));
    }

    public List<String> siteAreaImagesUrl() {
        return siteAreas.stream()
                .flatMap(siteArea -> siteArea.getImages().stream())  // Flatten each list of SiteAreaImageDto objects
                .map(SiteAreaImageDto::getImageUrl)                  // Map each SiteAreaImageDto to its imageUrl
                .collect(Collectors.toCollection(ArrayList::new));   // Collect into an ArrayList of Strings
    }


}
