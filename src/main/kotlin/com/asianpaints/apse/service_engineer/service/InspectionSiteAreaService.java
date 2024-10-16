package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.dto.SiteAreaDto;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.SiteAreaNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SiteAreaMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.repository.SiteAreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspectionSiteAreaService {

    private final SiteAreaMapper siteAreaMapper;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final SiteAreaRepository siteAreaRepository;

    public InspectionSiteAreaService(SiteAreaMapper siteAreaMapper,
                                     InspectionSiteRepository inspectionSiteRepository,
                                     SiteAreaRepository siteAreaRepository) {

        this.inspectionSiteRepository = inspectionSiteRepository;
        this.siteAreaMapper = siteAreaMapper;
        this.siteAreaRepository = siteAreaRepository;
    }

    public SiteAreaDto createSiteArea(SiteAreaDto siteAreaDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteAreaDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteAreaDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteArea siteArea = siteAreaMapper.toEntity(siteAreaDto,inspectionSite);
        SiteArea persistedSiteArea = siteAreaRepository.save(siteArea);
        return siteAreaMapper.toDto(persistedSiteArea);
    }

    public SiteAreaDto editSiteArea(Long id, SiteAreaDto siteAreaDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteAreaDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteAreaDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteArea siteArea = siteAreaRepository.findById(id).orElse(null);
        if (siteArea == null) {
            String errMsg = String.format("SiteArea with id %s does not exist in system", id);
            throw new SiteAreaNotFoundException(errMsg);
        }
        SiteArea editedSiteArea = siteAreaMapper.toEditEntity(siteArea,siteAreaDto,inspectionSite);
        SiteArea persistedSiteArea = siteAreaRepository.save(editedSiteArea);
        return siteAreaMapper.toDto(persistedSiteArea);
    }

    public void deleteSiteArea(Long id){
        SiteArea siteArea = siteAreaRepository.findById(id).orElse(null);
        if (siteArea == null) {
            String errMsg = String.format("SiteArea with id %s does not exist in system", id);
            throw new SiteAreaNotFoundException(errMsg);
        }
        siteAreaRepository.deleteById(id);
    }

    public List<SiteAreaDto> getAllSiteAreaByInspectionId(Long inspectionId){
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionId).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionId);
            throw new InspectionSiteNotFound(errMsg);
        }
        List<SiteArea> siteAreas = siteAreaRepository.findByInspectionSiteId(inspectionId);
        return siteAreas.stream()
                .map(siteAreaMapper::toDto)
                .collect(Collectors.toList());
    }

}
