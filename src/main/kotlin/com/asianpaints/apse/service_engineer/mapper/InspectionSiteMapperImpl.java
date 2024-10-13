package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InspectionSiteMapperImpl implements InspectionSiteMapper{
    @Override
    public InspectionSite toEntity(InspectionSiteRequest inspectionSiteRequest, ApUser user) {
        return InspectionSite.builder()
                .siteId(inspectionSiteRequest.getSiteId())
                .createdOn(LocalDateTime.now())
                .reportName(inspectionSiteRequest.getReportName())
                .city(inspectionSiteRequest.getCity())
                .state(inspectionSiteRequest.getState())
                .conductedAt(inspectionSiteRequest.getConductedAt())
                .user(user)
                .imageUrl(inspectionSiteRequest.getImageUrl())
                .status(InspectionSiteStatus.Draft)
                .build();
     }

    @Override
    public InspectionSite toEditEntity(InspectionSite inspectionSite, InspectionSiteRequest inspectionSiteRequest, ApUser user) {
        InspectionSite.InspectionSiteBuilder inspectionSiteBuilder = inspectionSite.toBuilder();
        return inspectionSiteBuilder
                .siteId(inspectionSiteRequest.getSiteId())
                .reportName(inspectionSiteRequest.getReportName())
                .city(inspectionSiteRequest.getCity())
                .state(inspectionSiteRequest.getState())
                .conductedAt(inspectionSiteRequest.getConductedAt())
                .user(user)
                .imageUrl(inspectionSiteRequest.getImageUrl())
                .build();

    }

    @Override
    public InspectionSiteResponse toDto(InspectionSite inspectionSite) {
        return InspectionSiteResponse.builder()
                .createdOn(inspectionSite.getCreatedOn())
                .id(inspectionSite.getId())
                .siteId(inspectionSite.getSiteId())
                .status(inspectionSite.getStatus())
                .conductedAt(inspectionSite.getConductedAt())
                .state(inspectionSite.getState())
                .reportName(inspectionSite.getReportName())
                .build();
    }

}
