package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentResponse;
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
                .imageUrl1(siteCorrosivityEnvironmentDto.getImageUrl1())
                .imageUrl2(siteCorrosivityEnvironmentDto.getImageUrl2())
                .imageUrl3(siteCorrosivityEnvironmentDto.getImageUrl3())
                .siteAreas(siteAreas)
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
                .imageUrl1(siteCorrosivityEnvironmentDto.getImageUrl1())
                .imageUrl2(siteCorrosivityEnvironmentDto.getImageUrl2())
                .imageUrl3(siteCorrosivityEnvironmentDto.getImageUrl3())
                .siteAreas(siteAreas)
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
                .imageUrl1(siteCorrosivityEnvironment.getImageUrl1())
                .imageUrl2(siteCorrosivityEnvironment.getImageUrl2())
                .imageUrl3(siteCorrosivityEnvironment.getImageUrl3())
                .siteAreas(getAreas(siteCorrosivityEnvironment.getSiteAreas()))
                .build();
    }

    private Set<SiteAreaDto> getAreas(Set<SiteArea> siteAreas) {
        return siteAreas.stream()
                .map(siteArea -> {
                            return SiteAreaDto.builder()
                                    .id(siteArea.getId())
                                    .coatingCondition(siteArea.getCoatingCondition())
                                    .corrosionType(siteArea.getCorrosionType())
                                    .rating(siteArea.getRating())
                                    .area(siteArea.getArea())
                                    .build();
                        }
                ).collect(Collectors.toSet());

    }
}
