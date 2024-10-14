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
@Table(name = "SITE_CORROSIVITY_ENVIRONMENT")
public class SiteCorrosivityEnvironment {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "inspection_site_id")
    private Long inspectionSiteId;
    @Column(name = "rusting_degree")
    private String rustingDegree;
    @Column(name = "cracking_size")
    private Integer crackingSize;
    @Column(name = "cracking_qty")
    private Integer crackingQty;
    private String pattern;
    @Column(name = "blistering_size")
    private Integer blisteringSize;
    @Column(name = "blistering_qty")
    private Integer blisteringQty;
    @Column(name = "flaking_size")
    private Integer flakingSize;
    @Column(name = "flaking_qty")
    private Integer flakingQty;
    private String color;
    @Column(name = "overall_appearance")
    private String overallAppearance;
    @Column(name = "image_url_1")
    private String imageUrl1;
    @Column(name = "image_url_2")
    private String imageUrl2;
    @Column(name = "image_url_3")
    private String imageUrl3;
}
