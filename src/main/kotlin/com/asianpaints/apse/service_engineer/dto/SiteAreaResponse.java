package com.asianpaints.apse.service_engineer.dto;


import com.asianpaints.apse.service_engineer.domain.entity.SiteAreaImages;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
    private List<SiteAreaImages> images;
}
