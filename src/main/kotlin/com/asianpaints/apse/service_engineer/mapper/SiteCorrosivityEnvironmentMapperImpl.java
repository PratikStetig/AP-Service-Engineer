package com.asianpaints.apse.service_engineer.mapper;

import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;
import org.springframework.stereotype.Component;

@Component
public class SiteCorrosivityEnvironmentMapperImpl implements SiteCorrosivityEnvironmentMapper {
    @Override
    public SiteCorrosivityEnvironment toEntity(SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        return SiteCorrosivityEnvironment.builder()
                .inspectionSiteId(siteCorrosivityEnvironmentDto.getInspectionSiteId())
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
                .build();
    }

    @Override
    public void toEditEntity(SiteCorrosivityEnvironment siteCorrosivityEnvironment, SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {

        SiteCorrosivityEnvironment.SiteCorrosivityEnvironmentBuilder builder = siteCorrosivityEnvironment.toBuilder();
        builder
                .inspectionSiteId(siteCorrosivityEnvironmentDto.getInspectionSiteId())
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
                .build();

    }

    @Override
    public SiteCorrosivityEnvironmentDto toDto(SiteCorrosivityEnvironment siteCorrosivityEnvironment) {
        return SiteCorrosivityEnvironmentDto.builder()
                .inspectionSiteId(siteCorrosivityEnvironment.getInspectionSiteId())
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
                .build();
    }
}
