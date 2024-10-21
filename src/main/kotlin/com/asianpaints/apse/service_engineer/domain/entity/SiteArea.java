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
    public Long id;
    @ManyToOne
    @JoinColumn(name = "inspection_site_id")
    public InspectionSite inspectionSite;
    public String area;
    @Column(name = "coating_condition")
    public String coatingCondition;
    @Column(name = "corrosion_type")
    @Enumerated(EnumType.STRING)
    public CorrosionType corrosionType;
    public Integer rating;
}
