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
@Table(name = "INSPECTION_SITE_ACKNOWLEDGEMENT")
public class InspectionSiteAcknowledgement {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "inspection_site_id")
    private Long inspectionSiteId;
    @Column(name = "person_name")
    private String personName;
    private String designation;
}
