package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SitePreliminaryObservation;
import com.asianpaints.apse.service_engineer.dto.SitePreliminaryObservationDto;

public interface SitePreliminaryObservationMapper {
    SitePreliminaryObservation toEntity(SitePreliminaryObservationDto sitePreliminaryObservationDto);
    SitePreliminaryObservation toEditEntity(SitePreliminaryObservation SitePreliminaryObservation,SitePreliminaryObservationDto sitePreliminaryObservationDto);
    SitePreliminaryObservationDto toDto(SitePreliminaryObservation SitePreliminaryObservation);
}
