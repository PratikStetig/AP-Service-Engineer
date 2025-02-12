package com.asianpaints.apse.service_engineer.dto;


import com.asianpaints.apse.service_engineer.domain.entity.SiteAreaImages;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private String structureType;
    private String corrosionType;
    private Integer rating;
    private Set<SiteAreaImages> images;
}
