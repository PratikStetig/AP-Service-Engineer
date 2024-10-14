package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;
import org.springframework.stereotype.Component;

@Component
public class SiteAreaMapperImpl implements SiteAreaMapper{
    @Override
    public SiteArea toEntity(SiteAreaDto siteAreaDto) {
        return SiteArea.builder()
                .inspectionSiteId(siteAreaDto.getInspectionSiteId())
                .coatingCondition(siteAreaDto.getCoatingCondition())
                .corrosionType(siteAreaDto.getCorrosionType())
                .rating(siteAreaDto.getRating())
                .area(siteAreaDto.getArea())
                .build();
    }

    @Override
    public void toEditEntity(SiteArea siteArea, SiteAreaDto siteAreaDto) {
        SiteArea.SiteAreaBuilder builder = siteArea.toBuilder();
        builder
                .inspectionSiteId(siteAreaDto.getInspectionSiteId())
                .coatingCondition(siteAreaDto.getCoatingCondition())
                .corrosionType(siteAreaDto.getCorrosionType())
                .rating(siteAreaDto.getRating())
                .area(siteAreaDto.getArea())
                .build();
    }

    @Override
    public SiteAreaDto toDto(SiteArea siteArea) {
        return SiteAreaDto.builder()
                .inspectionSiteId(siteArea.getInspectionSiteId())
                .coatingCondition(siteArea.getCoatingCondition())
                .corrosionType(siteArea.getCorrosionType())
                .rating(siteArea.getRating())
                .area(siteArea.getArea())
                .build();
    }
}
