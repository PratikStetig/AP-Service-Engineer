package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentResponse;

import java.util.List;
import java.util.Set;

public interface SiteCorrosivityEnvironmentMapper {
    SiteCorrosivityEnvironment toEntity(SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto,
                                        InspectionSite inspectionSite,
                                        Set<SiteArea> siteAreas);
    SiteCorrosivityEnvironment toEditEntity(SiteCorrosivityEnvironment siteCorrosivityEnvironment,
                                                    SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto,
                                                    InspectionSite inspectionSite,
                                                    Set<SiteArea> siteAreas);
    SiteCorrosivityEnvironmentResponse toDto(SiteCorrosivityEnvironment siteCorrosivityEnvironment);
}
