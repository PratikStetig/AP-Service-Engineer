package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteMoreInformation;
import com.asianpaints.apse.service_engineer.dto.SiteMoreInformationDto;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.SiteMoreInformationNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SiteMoreInformationMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.repository.SiteMoreInformationRepository;
import org.springframework.stereotype.Service;

@Service
public class SiteMoreInformationService {

    private final SiteMoreInformationRepository siteMoreInformationRepository;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final SiteMoreInformationMapper siteMoreInformationMapper;

    public SiteMoreInformationService(SiteMoreInformationRepository siteMoreInformationRepository,
                                      InspectionSiteRepository inspectionSiteRepository,
                                      SiteMoreInformationMapper siteMoreInformationMapper) {
        this.siteMoreInformationRepository = siteMoreInformationRepository;
        this.inspectionSiteRepository = inspectionSiteRepository;
        this.siteMoreInformationMapper = siteMoreInformationMapper;
    }

    public SiteMoreInformationDto createSiteMoreInformation(SiteMoreInformationDto siteMoreInformationDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteMoreInformationDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteMoreInformationDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteMoreInformation siteMoreInformation = siteMoreInformationMapper.toEntity(siteMoreInformationDto);
        SiteMoreInformation persistedSiteMoreInformation = siteMoreInformationRepository.save(siteMoreInformation);
        return siteMoreInformationMapper.toDto(persistedSiteMoreInformation);
    }

    public SiteMoreInformationDto editSiteMoreInformation(Long id, SiteMoreInformationDto siteMoreInformationDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteMoreInformationDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteMoreInformationDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteMoreInformation siteMoreInformation = siteMoreInformationRepository.findById(id).orElse(null);
        if (siteMoreInformation == null) {
            String errMsg = String.format("siteMoreInformation with id %s does not exist in system", id);
            throw new SiteMoreInformationNotFoundException(errMsg);
        }
        siteMoreInformationMapper.toEditEntity(siteMoreInformation, siteMoreInformationDto);
        SiteMoreInformation persistedSiteMoreInformation = siteMoreInformationRepository.save(siteMoreInformation);
        return siteMoreInformationMapper.toDto(persistedSiteMoreInformation);
    }

    public void deleteSiteMoreInformation(Long id) {
        SiteMoreInformation siteMoreInformation = siteMoreInformationRepository.findById(id).orElse(null);
        if (siteMoreInformation == null) {
            String errMsg = String.format("siteMoreInformation with id %s does not exist in system", id);
            throw new SiteMoreInformationNotFoundException(errMsg);
        }
        siteMoreInformationRepository.deleteById(id);
    }

    public SiteMoreInformationDto getSiteMoreInformation(Long id) {
        SiteMoreInformation siteMoreInformation = siteMoreInformationRepository.findById(id).orElse(null);
        if (siteMoreInformation == null) {
            String errMsg = String.format("siteMoreInformation with id %s does not exist in system", id);
            throw new SiteMoreInformationNotFoundException(errMsg);
        }
        return siteMoreInformationMapper.toDto(siteMoreInformation);
    }
}
