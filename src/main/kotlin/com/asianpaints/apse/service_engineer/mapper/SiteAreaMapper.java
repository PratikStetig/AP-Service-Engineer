package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;

public interface SiteAreaMapper {

    SiteArea toEntity(SiteAreaDto siteAreaDto);

    void toEditEntity(SiteArea siteArea,
                      SiteAreaDto siteAreaDto);

    SiteAreaDto toDto(SiteArea siteArea);
}
