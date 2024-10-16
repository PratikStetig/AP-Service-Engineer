package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SitePreliminaryObservation;
import com.asianpaints.apse.service_engineer.dto.SitePreliminaryObservationDto;

public interface SitePreliminaryObservationMapper {
    SitePreliminaryObservation toEntity(SitePreliminaryObservationDto sitePreliminaryObservationDto, InspectionSite inspectionSite);
    SitePreliminaryObservation toEditEntity(SitePreliminaryObservation SitePreliminaryObservation,SitePreliminaryObservationDto sitePreliminaryObservationDto,InspectionSite inspectionSite);
    SitePreliminaryObservationDto toDto(SitePreliminaryObservation SitePreliminaryObservation);
}
