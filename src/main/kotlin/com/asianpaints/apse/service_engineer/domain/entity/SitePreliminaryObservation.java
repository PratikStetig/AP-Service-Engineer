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
@Table(name = "SITE_PRELIMINARY_OBSERVATION")
public class SitePreliminaryObservation {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "inspection_site_id")
    public InspectionSite inspectionSite;
    public boolean ruralArea;
    public boolean urbanArea;
    public boolean coastalArea;
    public boolean industrialPollutedArea;
    public String chemicalsExposed;
    public boolean salineAtmosphere;
    public double averageHumidity;
    public String description;
    public String environmentClassification;
}
