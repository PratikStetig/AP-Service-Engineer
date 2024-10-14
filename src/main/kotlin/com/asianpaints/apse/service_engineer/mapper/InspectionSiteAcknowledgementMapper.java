package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto;

public interface InspectionSiteAcknowledgementMapper {

    InspectionSiteAcknowledgement toEntity(InspectionSiteAckDto inspectionSiteAckDto);

    InspectionSiteAcknowledgement toEditEntity(InspectionSiteAcknowledgement inspectionSiteAcknowledgement,
                                               InspectionSiteAckDto inspectionSiteAckDto);

    InspectionSiteAckDto toDto(InspectionSiteAcknowledgement inspectionSiteAcknowledgement);
}
