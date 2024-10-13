package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto;

public class InspectionSiteAcknowledgmentMapperImpl implements InspectionSiteAcknowledgementMapper {

    @Override
    public InspectionSiteAcknowledgement toEntity(InspectionSiteAckDto inspectionSiteAckDto) {
        return InspectionSiteAcknowledgement.builder()
                .inspectionSiteId(inspectionSiteAckDto.getInspectionId())
                .personName(inspectionSiteAckDto.getPersonName())
                .designation(inspectionSiteAckDto.getDesignation())
                .build();
    }

    @Override
    public InspectionSiteAcknowledgement toEditEntity(InspectionSiteAcknowledgement inspectionSiteAcknowledgement, InspectionSiteAckDto inspectionSiteAckDto) {
        InspectionSiteAcknowledgement.InspectionSiteAcknowledgementBuilder builder = inspectionSiteAcknowledgement.toBuilder();
        return builder
                .inspectionSiteId(inspectionSiteAckDto.getInspectionId())
                .personName(inspectionSiteAckDto.getPersonName())
                .designation(inspectionSiteAckDto.getDesignation())
                .build();
    }

    @Override
    public InspectionSiteAckDto toDto(InspectionSiteAcknowledgement inspectionSiteAcknowledgement) {
        return InspectionSiteAckDto.builder()
                .personName(inspectionSiteAcknowledgement.getPersonName())
                .designation(inspectionSiteAcknowledgement.getDesignation())
                .id(inspectionSiteAcknowledgement.getId())
                .build();
    }
}
