package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto;
import org.springframework.stereotype.Component;

@Component
public class InspectionSiteAcknowledgmentMapperImpl implements InspectionSiteAcknowledgementMapper {

    @Override
    public InspectionSiteAcknowledgement toEntity(InspectionSiteAckDto inspectionSiteAckDto, InspectionSite inspectionSite) {
        return InspectionSiteAcknowledgement.builder()
                .inspectionSite(inspectionSite)
                .personName(inspectionSiteAckDto.getPersonName())
                .designation(inspectionSiteAckDto.getDesignation())
                .build();
    }

    @Override
    public InspectionSiteAcknowledgement toEditEntity(InspectionSiteAcknowledgement inspectionSiteAcknowledgement, InspectionSiteAckDto inspectionSiteAckDto,InspectionSite inspectionSite) {
        InspectionSiteAcknowledgement.InspectionSiteAcknowledgementBuilder builder = inspectionSiteAcknowledgement.toBuilder();
        return builder
                .inspectionSite(inspectionSite)
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
                .inspectionId(inspectionSiteAcknowledgement.getInspectionSite().getId())
                .build();
    }
}
