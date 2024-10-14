package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SitePreliminaryObservation;
import com.asianpaints.apse.service_engineer.dto.SitePreliminaryObservationDto;
import org.springframework.stereotype.Component;

@Component
public class SitePreliminaryObservationMapperImpl implements SitePreliminaryObservationMapper {
    @Override
    public SitePreliminaryObservation toEntity(SitePreliminaryObservationDto sitePreliminaryObservationDto) {
        return SitePreliminaryObservation.builder()
                .inspectionSiteId(sitePreliminaryObservationDto.getInspectionSiteId())
                .ruralArea(sitePreliminaryObservationDto.isRuralArea())
                .urbanArea(sitePreliminaryObservationDto.isUrbanArea())
                .coastalArea(sitePreliminaryObservationDto.isCoastalArea())
                .industrialPollutedArea(sitePreliminaryObservationDto.isIndustrialPollutedArea())
                .chemicalsExposed(sitePreliminaryObservationDto.getChemicalsExposed())
                .salineAtmosphere(sitePreliminaryObservationDto.isSalineAtmosphere())
                .averageHumidity(sitePreliminaryObservationDto.getAverageHumidity())
                .description(sitePreliminaryObservationDto.getDescription())
                .build();
    }

    @Override
    public SitePreliminaryObservation toEditEntity(SitePreliminaryObservation sitePreliminaryObservation, SitePreliminaryObservationDto sitePreliminaryObservationDto) {
        SitePreliminaryObservation.SitePreliminaryObservationBuilder builder = sitePreliminaryObservation.toBuilder();
        return builder.inspectionSiteId(sitePreliminaryObservationDto.getInspectionSiteId())
                .ruralArea(sitePreliminaryObservationDto.isRuralArea())
                .urbanArea(sitePreliminaryObservationDto.isUrbanArea())
                .coastalArea(sitePreliminaryObservationDto.isCoastalArea())
                .industrialPollutedArea(sitePreliminaryObservationDto.isIndustrialPollutedArea())
                .chemicalsExposed(sitePreliminaryObservationDto.getChemicalsExposed())
                .salineAtmosphere(sitePreliminaryObservationDto.isSalineAtmosphere())
                .averageHumidity(sitePreliminaryObservationDto.getAverageHumidity())
                .description(sitePreliminaryObservationDto.getDescription())
                .build();
    }

    @Override
    public SitePreliminaryObservationDto toDto(SitePreliminaryObservation sitePreliminaryObservation) {
        return SitePreliminaryObservationDto.builder()
                .inspectionSiteId(sitePreliminaryObservation.getInspectionSiteId())
                .ruralArea(sitePreliminaryObservation.isRuralArea())
                .urbanArea(sitePreliminaryObservation.isUrbanArea())
                .coastalArea(sitePreliminaryObservation.isCoastalArea())
                .industrialPollutedArea(sitePreliminaryObservation.isIndustrialPollutedArea())
                .chemicalsExposed(sitePreliminaryObservation.getChemicalsExposed())
                .salineAtmosphere(sitePreliminaryObservation.isSalineAtmosphere())
                .averageHumidity(sitePreliminaryObservation.getAverageHumidity())
                .description(sitePreliminaryObservation.getDescription())
                .build();
    }
}
