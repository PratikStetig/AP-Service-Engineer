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
    private InspectionSite inspectionSite;
    private boolean ruralArea;
    private boolean urbanArea;
    private boolean coastalArea;
    private boolean industrialPollutedArea;
    private String chemicalsExposed;
    private boolean salineAtmosphere;
    private double averageHumidity;
    private String description;

}
