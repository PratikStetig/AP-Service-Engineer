package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InspectionSiteMapperImpl implements InspectionSiteMapper {
    @Override
    public InspectionSite toEntity(InspectionSiteRequest inspectionSiteRequest, ApUser user) {
        return InspectionSite.builder()
                .siteId(inspectionSiteRequest.getSiteId())
                .createdOn(LocalDateTime.now())
                .reportName(inspectionSiteRequest.getReportName())
                .city(inspectionSiteRequest.getCity())
                .state(inspectionSiteRequest.getState())
                .conductedAt(inspectionSiteRequest.getConductedAt())
                .conductedBy(user)
                .imageUrl(inspectionSiteRequest.getImageUrl())
                .status(InspectionSiteStatus.Draft)
                .inspectionDate(inspectionSiteRequest.getInspectionDate())
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
                .conductedBy(user)
                .imageUrl(inspectionSiteRequest.getImageUrl())
                .inspectionDate(inspectionSiteRequest.getInspectionDate())
                .build();

    }

    @Override
    public InspectionSiteResponse toDto(InspectionSite inspectionSite) {
        return InspectionSiteResponse.builder()
                .id(inspectionSite.getId())
                .siteId(inspectionSite.getSiteId())
                .createdOn(inspectionSite.getCreatedOn())
                .reportName(inspectionSite.getReportName())
                .city(inspectionSite.getCity())
                .state(inspectionSite.getState())
                .conductedAt(inspectionSite.getConductedAt())
                .conductedBy(inspectionSite.getConductedBy().getId())
                .imageUrl(inspectionSite.getImageUrl())
                .status(inspectionSite.getStatus())
                .inspectionDate(inspectionSite.getInspectionDate())
                .build();
    }

    public List<InspectionSiteResponse> toDtoList(List<InspectionSite> inspectionSites) {
        return inspectionSites.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
