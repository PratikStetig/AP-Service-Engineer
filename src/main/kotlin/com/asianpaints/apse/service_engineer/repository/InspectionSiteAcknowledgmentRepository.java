package com.asianpaints.apse.service_engineer.repository;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectionSiteAcknowledgmentRepository extends JpaRepository<InspectionSiteAcknowledgement,Long> {

    List<InspectionSiteAcknowledgement> findByInspectionSiteId(Long inspectionSiteId);
}
