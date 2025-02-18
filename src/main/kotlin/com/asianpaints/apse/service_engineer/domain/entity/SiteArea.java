package com.asianpaints.apse.service_engineer.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SITE_AREA")
public class SiteArea {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long id;
    @ManyToOne
    @JoinColumn(name = "inspection_site_id")
    public InspectionSite inspectionSite;
    public String area;
    @Column(name = "coating_condition")
    public String coatingCondition;
    @Column(name = "structure_type")
    public String structureType;
    @Column(name = "corrosion_type")
    @Enumerated(EnumType.STRING)
    public CorrosionType corrosionType;
    public Integer rating;
//    @OneToMany(mappedBy = "siteArea", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @OneToMany(mappedBy = "siteArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
//    public List<SiteAreaImages> images = new ArrayList<>();

    @OneToMany(mappedBy = "siteArea", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonManagedReference
    public Set<SiteAreaImages> images = Collections.emptySet();

    public String getStructureTypeWithRating() {
        return structureType + " - " + rating + (corrosionType == CorrosionType.Scattered ? "S" : "L");
    }
}
