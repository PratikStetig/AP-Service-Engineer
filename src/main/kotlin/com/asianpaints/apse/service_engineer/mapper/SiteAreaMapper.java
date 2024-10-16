package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;

public interface SiteAreaMapper {

    SiteArea toEntity(SiteAreaDto siteAreaDto, InspectionSite inspectionSite);

    SiteArea toEditEntity(SiteArea siteArea,
                      SiteAreaDto siteAreaDto,
                      InspectionSite inspectionSite);

    SiteAreaDto toDto(SiteArea siteArea);
}
