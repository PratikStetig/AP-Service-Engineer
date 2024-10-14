package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;

public interface SiteCorrosivityEnvironmentMapper {
    SiteCorrosivityEnvironment toEntity(SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto);
    void toEditEntity(SiteCorrosivityEnvironment siteCorrosivityEnvironment,SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto);
    SiteCorrosivityEnvironmentDto toDto(SiteCorrosivityEnvironment siteCorrosivityEnvironment);
}
