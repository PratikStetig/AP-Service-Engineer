package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto;

public interface InspectionSiteAcknowledgementMapper {

    InspectionSiteAcknowledgement toEntity(InspectionSiteAckDto inspectionSiteAckDto, InspectionSite inspectionSite);

    InspectionSiteAcknowledgement toEditEntity(InspectionSiteAcknowledgement inspectionSiteAcknowledgement,
                                               InspectionSiteAckDto inspectionSiteAckDto,
                                               InspectionSite inspectionSite);

    InspectionSiteAckDto toDto(InspectionSiteAcknowledgement inspectionSiteAcknowledgement);
}
