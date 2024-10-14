package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteMoreInformation;
import com.asianpaints.apse.service_engineer.dto.SiteMoreInformationDto;
import org.springframework.stereotype.Component;

@Component
public class SiteMoreInformationMapperImpl implements SiteMoreInformationMapper {

    @Override
    public SiteMoreInformation toEntity(SiteMoreInformationDto siteMoreInformationDto) {
        return SiteMoreInformation.builder()
                .inspectionSiteId(siteMoreInformationDto.getInspectionSiteId())
                .possibleSurfacePreparation(siteMoreInformationDto.getPossibleSurfacePreparation())
                .recoatingInterval(siteMoreInformationDto.getRecoatingInterval())
                .serviceLife(siteMoreInformationDto.getServiceLife())
                .shade(siteMoreInformationDto.getShade())
                .aesthetic(siteMoreInformationDto.getAesthetic())
                .existingPaintingSystem(siteMoreInformationDto.getExistingPaintingSystem())
                .dftExistingSystem(siteMoreInformationDto.getDftExistingSystem())
                .remarks(siteMoreInformationDto.getRemarks())
                .build();
    }

    @Override
    public void toEditEntity(SiteMoreInformation siteMoreInformation, SiteMoreInformationDto siteMoreInformationDto) {
        SiteMoreInformation.SiteMoreInformationBuilder builder = siteMoreInformation.toBuilder();
        builder
                .inspectionSiteId(siteMoreInformationDto.getInspectionSiteId())
                .possibleSurfacePreparation(siteMoreInformationDto.getPossibleSurfacePreparation())
                .recoatingInterval(siteMoreInformationDto.getRecoatingInterval())
                .serviceLife(siteMoreInformationDto.getServiceLife())
                .shade(siteMoreInformationDto.getShade())
                .aesthetic(siteMoreInformationDto.getAesthetic())
                .existingPaintingSystem(siteMoreInformationDto.getExistingPaintingSystem())
                .dftExistingSystem(siteMoreInformationDto.getDftExistingSystem())
                .remarks(siteMoreInformationDto.getRemarks())
                .build();
    }

    @Override
    public SiteMoreInformationDto toDto(SiteMoreInformation siteMoreInformation) {
        return SiteMoreInformationDto.builder()
                .inspectionSiteId(siteMoreInformation.getInspectionSiteId())
                .possibleSurfacePreparation(siteMoreInformation.getPossibleSurfacePreparation())
                .recoatingInterval(siteMoreInformation.getRecoatingInterval())
                .serviceLife(siteMoreInformation.getServiceLife())
                .shade(siteMoreInformation.getShade())
                .aesthetic(siteMoreInformation.getAesthetic())
                .existingPaintingSystem(siteMoreInformation.getExistingPaintingSystem())
                .dftExistingSystem(siteMoreInformation.getDftExistingSystem())
                .remarks(siteMoreInformation.getRemarks())
                .build();
    }
}
