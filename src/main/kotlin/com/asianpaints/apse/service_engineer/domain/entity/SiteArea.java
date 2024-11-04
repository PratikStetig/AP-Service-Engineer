package com.asianpaints.apse.service_engineer.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "siteArea", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SiteAreaImages> images = new ArrayList<>();
}
