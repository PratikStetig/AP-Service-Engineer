package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.SiteCorrosivityEnvironmentNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SiteCorrosivityEnvironmentMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.repository.SiteCorrosivityEnvironmentRepository;
import org.springframework.stereotype.Service;

@Service
public class SiteCorrosivityEnvironmentService {

    private final SiteCorrosivityEnvironmentRepository siteCorrosivityEnvironmentRepository;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final SiteCorrosivityEnvironmentMapper siteCorrosivityEnvironmentMapper;

    public SiteCorrosivityEnvironmentService(SiteCorrosivityEnvironmentRepository siteCorrosivityEnvironmentRepository,
                                             InspectionSiteRepository inspectionSiteRepository,
                                             SiteCorrosivityEnvironmentMapper siteCorrosivityEnvironmentMapper) {
        this.siteCorrosivityEnvironmentRepository = siteCorrosivityEnvironmentRepository;
        this.inspectionSiteRepository = inspectionSiteRepository;
        this.siteCorrosivityEnvironmentMapper = siteCorrosivityEnvironmentMapper;
    }

    public SiteCorrosivityEnvironmentDto createSiteCorrosivityEnvironment(SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteCorrosivityEnvironmentDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteCorrosivityEnvironmentDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentMapper.toEntity(siteCorrosivityEnvironmentDto);
        SiteCorrosivityEnvironment persistedSiteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.save(siteCorrosivityEnvironment);
        return siteCorrosivityEnvironmentMapper.toDto(persistedSiteCorrosivityEnvironment);
    }

    public SiteCorrosivityEnvironmentDto editSiteCorrosivityEnvironment(Long id, SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteCorrosivityEnvironmentDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteCorrosivityEnvironmentDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.findById(id).orElse(null);
        if (siteCorrosivityEnvironment == null) {
            String errMsg = String.format("siteCorrosivityEnvironment with id %s does not exist in system", id);
            throw new SiteCorrosivityEnvironmentNotFoundException(errMsg);
        }
        siteCorrosivityEnvironmentMapper.toEditEntity(siteCorrosivityEnvironment, siteCorrosivityEnvironmentDto);
        SiteCorrosivityEnvironment persistedSiteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.save(siteCorrosivityEnvironment);
        return siteCorrosivityEnvironmentMapper.toDto(persistedSiteCorrosivityEnvironment);
    }

    public void deleteSiteCorrosivityEnvironmentDto(Long id) {
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.findById(id).orElse(null);
        if (siteCorrosivityEnvironment == null) {
            String errMsg = String.format("siteCorrosivityEnvironment with id %s does not exist in system", id);
            throw new SiteCorrosivityEnvironmentNotFoundException(errMsg);
        }
        siteCorrosivityEnvironmentRepository.deleteById(id);
    }

    public SiteCorrosivityEnvironmentDto getSitePreliminaryObservation(Long id) {
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.findById(id).orElse(null);
        if (siteCorrosivityEnvironment == null) {
            String errMsg = String.format("siteCorrosivityEnvironment with id %s does not exist in system", id);
            throw new SiteCorrosivityEnvironmentNotFoundException(errMsg);
        }
        return siteCorrosivityEnvironmentMapper.toDto(siteCorrosivityEnvironment);
    }
}
