package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteMoreInformation;
import com.asianpaints.apse.service_engineer.dto.SiteMoreInformationDto;

public interface SiteMoreInformationMapper {
    SiteMoreInformation toEntity(SiteMoreInformationDto siteMoreInformationDto);
    void toEditEntity(SiteMoreInformation siteMoreInformation,SiteMoreInformationDto siteMoreInformationDto);
    SiteMoreInformationDto toDto(SiteMoreInformation siteMoreInformation);
}
