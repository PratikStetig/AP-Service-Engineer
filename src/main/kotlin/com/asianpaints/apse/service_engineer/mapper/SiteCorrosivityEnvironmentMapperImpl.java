package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.domain.entity.SiteAreaImages;
import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;
import com.asianpaints.apse.service_engineer.dto.SiteAreaImageDto;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentResponse;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SiteCorrosivityEnvironmentMapperImpl implements SiteCorrosivityEnvironmentMapper {
    @Override
    public SiteCorrosivityEnvironment toEntity(SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto,
                                               InspectionSite inspectionSite,
                                               Set<SiteArea> siteAreas) {
        return SiteCorrosivityEnvironment.builder()
                .inspectionSite(inspectionSite)
                .rustingDegree(siteCorrosivityEnvironmentDto.getRustingDegree())
                .crackingSize(siteCorrosivityEnvironmentDto.getCrackingSize())
                .crackingQty(siteCorrosivityEnvironmentDto.getCrackingQty())
                .pattern(siteCorrosivityEnvironmentDto.getPattern())
                .blisteringQty(siteCorrosivityEnvironmentDto.getBlisteringQty())
                .blisteringSize(siteCorrosivityEnvironmentDto.getBlisteringSize())
                .flakingSize(siteCorrosivityEnvironmentDto.getFlakingSize())
                .flakingQty(siteCorrosivityEnvironmentDto.getFlakingQty())
                .color(siteCorrosivityEnvironmentDto.getColor())
                .overallAppearance(siteCorrosivityEnvironmentDto.getOverallAppearance())
                .siteAreas(siteAreas)

                /*------------------ ADD MORE INFORMATION MERGE------------------------*/
                .possibleSurfacePreparation(siteCorrosivityEnvironmentDto.getPossibleSurfacePreparation())
                .recoatingInterval(siteCorrosivityEnvironmentDto.getRecoatingInterval())
                .recoatingIntervalOther(siteCorrosivityEnvironmentDto.getRecoatingIntervalOther())
                .serviceLife(siteCorrosivityEnvironmentDto.getServiceLife())
                .shade(siteCorrosivityEnvironmentDto.getShade())
                .aesthetic(siteCorrosivityEnvironmentDto.getAesthetic())
                .existingPaintingSystem(siteCorrosivityEnvironmentDto.getExistingPaintingSystem())
                .dftExistingSystem(siteCorrosivityEnvironmentDto.getDftExistingSystem())
                .remarks(siteCorrosivityEnvironmentDto.getRemarks())
                .build();
    }

    @Override
    public SiteCorrosivityEnvironment toEditEntity(SiteCorrosivityEnvironment siteCorrosivityEnvironment,
                                                   SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto,
                                                   InspectionSite inspectionSite,
                                                   Set<SiteArea> siteAreas) {

        SiteCorrosivityEnvironment.SiteCorrosivityEnvironmentBuilder builder = siteCorrosivityEnvironment.toBuilder();
        return builder
                .inspectionSite(inspectionSite)
                .rustingDegree(siteCorrosivityEnvironmentDto.getRustingDegree())
                .crackingSize(siteCorrosivityEnvironmentDto.getCrackingSize())
                .crackingQty(siteCorrosivityEnvironmentDto.getCrackingQty())
                .pattern(siteCorrosivityEnvironmentDto.getPattern())
                .blisteringQty(siteCorrosivityEnvironmentDto.getBlisteringQty())
                .blisteringSize(siteCorrosivityEnvironmentDto.getBlisteringSize())
                .flakingSize(siteCorrosivityEnvironmentDto.getFlakingSize())
                .flakingQty(siteCorrosivityEnvironmentDto.getFlakingQty())
                .color(siteCorrosivityEnvironmentDto.getColor())
                .overallAppearance(siteCorrosivityEnvironmentDto.getOverallAppearance())
                .siteAreas(siteAreas)

                /*------------------ ADD MORE INFORMATION MERGE------------------------*/
                .possibleSurfacePreparation(siteCorrosivityEnvironmentDto.getPossibleSurfacePreparation())
                .recoatingInterval(siteCorrosivityEnvironmentDto.getRecoatingInterval())
                .recoatingIntervalOther(siteCorrosivityEnvironmentDto.getRecoatingIntervalOther())
                .serviceLife(siteCorrosivityEnvironmentDto.getServiceLife())
                .shade(siteCorrosivityEnvironmentDto.getShade())
                .aesthetic(siteCorrosivityEnvironmentDto.getAesthetic())
                .existingPaintingSystem(siteCorrosivityEnvironmentDto.getExistingPaintingSystem())
                .dftExistingSystem(siteCorrosivityEnvironmentDto.getDftExistingSystem())
                .remarks(siteCorrosivityEnvironmentDto.getRemarks())
                .build();

    }

    @Override
    public SiteCorrosivityEnvironmentResponse toDto(SiteCorrosivityEnvironment siteCorrosivityEnvironment) {

        return SiteCorrosivityEnvironmentResponse.builder()
                .id(siteCorrosivityEnvironment.getId())
                .inspectionSiteId(siteCorrosivityEnvironment.getInspectionSite().getId())
                .rustingDegree(siteCorrosivityEnvironment.getRustingDegree())
                .crackingSize(siteCorrosivityEnvironment.getCrackingSize())
                .crackingQty(siteCorrosivityEnvironment.getCrackingQty())
                .pattern(siteCorrosivityEnvironment.getPattern())
                .blisteringQty(siteCorrosivityEnvironment.getBlisteringQty())
                .blisteringSize(siteCorrosivityEnvironment.getBlisteringSize())
                .flakingSize(siteCorrosivityEnvironment.getFlakingSize())
                .flakingQty(siteCorrosivityEnvironment.getFlakingQty())
                .color(siteCorrosivityEnvironment.getColor())
                .overallAppearance(siteCorrosivityEnvironment.getOverallAppearance())
                .siteAreas(getAreas(siteCorrosivityEnvironment.getSiteAreas()))

                /*------------------ ADD MORE INFORMATION MERGE------------------------*/
                .possibleSurfacePreparation(siteCorrosivityEnvironment.getPossibleSurfacePreparation())
                .recoatingInterval(siteCorrosivityEnvironment.getRecoatingInterval())
                .recoatingIntervalOther(siteCorrosivityEnvironment.getRecoatingIntervalOther())
                .serviceLife(siteCorrosivityEnvironment.getServiceLife())
                .shade(siteCorrosivityEnvironment.getShade())
                .aesthetic(siteCorrosivityEnvironment.getAesthetic())
                .existingPaintingSystem(siteCorrosivityEnvironment.getExistingPaintingSystem())
                .dftExistingSystem(siteCorrosivityEnvironment.getDftExistingSystem())
                .remarks(siteCorrosivityEnvironment.getRemarks())

                .build();
    }

    private Set<SiteAreaDto> getAreas(Set<SiteArea> siteAreas) {
        return siteAreas.stream()
                .map(siteArea ->
                        SiteAreaDto.builder()
                                .id(siteArea.getId())
                                .coatingCondition(siteArea.getCoatingCondition())
                                .corrosionType(siteArea.getCorrosionType())
                                .rating(siteArea.getRating())
                                .area(siteArea.getArea())
                                .images(getAreaImageDto(siteArea.getImages()))
                                .structureType(siteArea.getStructureType())
                                .build()
                ).collect(Collectors.toSet());
    }

    private List<SiteAreaImageDto> getAreaImageDto(List<SiteAreaImages> siteAreaImages) {
        return siteAreaImages.stream()
                .map(image -> SiteAreaImageDto.builder()
                        .id(image.getId())
                        .siteAreaId(image.getSiteArea().getId())
                        .imageUrl(image.getImageUrl())
                        .uploadedAt(image.getUploadedAt())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
