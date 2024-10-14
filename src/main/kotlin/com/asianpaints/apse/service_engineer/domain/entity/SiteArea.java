package com.asianpaints.apse.service_engineer.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SITE_AREA")
public class SiteArea {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "inspection_site_id")
    private Long inspectionSiteId;
    private String area;
    @Column(name = "coating_condition")
    private String coatingCondition;
    @Column(name = "corrosion_type")
    @Enumerated(EnumType.STRING)
    private CorrosionType corrosionType;
    private Integer rating;
}
