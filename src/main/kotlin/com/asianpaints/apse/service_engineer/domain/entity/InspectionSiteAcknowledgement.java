package com.asianpaints.apse.service_engineer.domain.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INSPECTION_SITE_ACKNOWLEDGEMENT")
public class InspectionSiteAcknowledgement {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "inspection_site_id")
    public InspectionSite inspectionSite;
    @Column(name = "person_name")
    public String personName;
    public String designation;
}
