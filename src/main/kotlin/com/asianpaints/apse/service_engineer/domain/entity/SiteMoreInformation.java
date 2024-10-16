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
@Table(name = "SITE_MORE_INFORMATION")
public class SiteMoreInformation {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "corrosivity_environment_id")
    private SiteCorrosivityEnvironment siteCorrosivityEnvironment;
    @Column(name = "possible_surface_preparation")
    private String possibleSurfacePreparation;
    @Column(name = "recoating_interval")
    private String recoatingInterval;
    @Column(name = "service_life")
    private String serviceLife;
    private String shade;
    private String aesthetic;
    @Column(name = "existing_painting_system")
    private String existingPaintingSystem;
    @Column(name = "dft_existing_system")
    private String dftExistingSystem;
    private String remarks;
}
