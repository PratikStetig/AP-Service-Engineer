package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;
import com.asianpaints.apse.service_engineer.dto.SiteAreaResponse;
import org.springframework.stereotype.Component;

@Component
public class SiteAreaMapperImpl implements SiteAreaMapper{
    @Override
    public SiteArea toEntity(SiteAreaDto siteAreaDto, InspectionSite inspectionSite) {
        return SiteArea.builder()
                .inspectionSite(inspectionSite)
                .coatingCondition(siteAreaDto.getCoatingCondition())
                .corrosionType(siteAreaDto.getCorrosionType())
                .rating(siteAreaDto.getRating())
                .area(siteAreaDto.getArea())
                .build();
    }

    @Override
    public SiteAreaResponse toResponse(SiteArea siteArea) {
        return SiteAreaResponse.builder()
                .id(siteArea.getId())
                .inspectionSiteId(siteArea.getInspectionSite().getId())
                .coatingCondition(siteArea.getCoatingCondition())
                .corrosionType(siteArea.getCorrosionType().name())
                .rating(siteArea.getRating())
                .area(siteArea.getArea())
                .images(siteArea.getImages())
                .build();
    }

    @Override
    public SiteArea toEditEntity(SiteArea siteArea, SiteAreaDto siteAreaDto, InspectionSite inspectionSite) {
        SiteArea.SiteAreaBuilder builder = siteArea.toBuilder();
        return builder
                .inspectionSite(inspectionSite)
                .coatingCondition(siteAreaDto.getCoatingCondition())
                .corrosionType(siteAreaDto.getCorrosionType())
                .rating(siteAreaDto.getRating())
                .area(siteAreaDto.getArea())
                .build();
    }

    @Override
    public SiteAreaDto toDto(SiteArea siteArea) {
        return SiteAreaDto.builder()
                .id(siteArea.getId())
                .inspectionSiteId(siteArea.getInspectionSite().getId())
                .coatingCondition(siteArea.getCoatingCondition())
                .corrosionType(siteArea.getCorrosionType())
                .rating(siteArea.getRating())
                .area(siteArea.getArea())
                .build();
    }
}
