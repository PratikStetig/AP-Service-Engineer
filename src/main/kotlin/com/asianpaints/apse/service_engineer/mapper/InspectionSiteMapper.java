package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;

import java.util.List;

public interface InspectionSiteMapper {
    InspectionSite toEntity(InspectionSiteRequest inspectionSiteRequest, ApUser user);
    InspectionSite toEditEntity(InspectionSite inspectionSite,
                                InspectionSiteRequest inspectionSiteRequest,
                                ApUser user);
    InspectionSiteResponse toDto(InspectionSite inspectionSite);

    List<InspectionSiteResponse> toDtoList(List<InspectionSite> inspectionSite);
}
