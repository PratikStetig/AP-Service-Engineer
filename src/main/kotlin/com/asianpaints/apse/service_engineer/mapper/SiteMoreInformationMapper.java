package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.domain.entity.SiteMoreInformation;
import com.asianpaints.apse.service_engineer.dto.SiteMoreInformationDto;

public interface SiteMoreInformationMapper {
    SiteMoreInformation toEntity(SiteMoreInformationDto siteMoreInformationDto, SiteCorrosivityEnvironment siteCorrosivityEnvironment);
    SiteMoreInformation toEditEntity(SiteMoreInformation siteMoreInformation,SiteMoreInformationDto siteMoreInformationDto, SiteCorrosivityEnvironment siteCorrosivityEnvironment);
    SiteMoreInformationDto toDto(SiteMoreInformation siteMoreInformation);
}
