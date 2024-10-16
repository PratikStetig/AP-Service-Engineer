package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.domain.entity.SiteMoreInformation;
import com.asianpaints.apse.service_engineer.dto.SiteMoreInformationDto;
import com.asianpaints.apse.service_engineer.exception.SiteCorrosivityEnvironmentNotFoundException;
import com.asianpaints.apse.service_engineer.exception.SiteMoreInformationNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SiteMoreInformationMapper;
import com.asianpaints.apse.service_engineer.repository.SiteCorrosivityEnvironmentRepository;
import com.asianpaints.apse.service_engineer.repository.SiteMoreInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiteMoreInformationService {

    private final SiteMoreInformationRepository siteMoreInformationRepository;
    private final SiteCorrosivityEnvironmentRepository siteCorrosivityEnvironmentRepository;
    private final SiteMoreInformationMapper siteMoreInformationMapper;


    public SiteMoreInformationDto createSiteMoreInformation(SiteMoreInformationDto siteMoreInformationDto) {
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.findById(siteMoreInformationDto.getCorrosivityEnvironmentId()).orElse(null);
        if (siteCorrosivityEnvironment == null) {
            String errMsg = String.format("SiteCorrosivityEnvironment with id %s does not exist in system", siteMoreInformationDto.getCorrosivityEnvironmentId());
            throw new SiteCorrosivityEnvironmentNotFoundException(errMsg);
        }
        SiteMoreInformation siteMoreInformation = siteMoreInformationMapper.toEntity(siteMoreInformationDto,siteCorrosivityEnvironment);
        SiteMoreInformation persistedSiteMoreInformation = siteMoreInformationRepository.save(siteMoreInformation);
        return siteMoreInformationMapper.toDto(persistedSiteMoreInformation);
    }

    public SiteMoreInformationDto editSiteMoreInformation(Long id, SiteMoreInformationDto siteMoreInformationDto) {
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.findById(siteMoreInformationDto.getCorrosivityEnvironmentId()).orElse(null);
        if (siteCorrosivityEnvironment == null) {
            String errMsg = String.format("SiteCorrosivityEnvironment with id %s does not exist in system", siteMoreInformationDto.getCorrosivityEnvironmentId());
            throw new SiteCorrosivityEnvironmentNotFoundException(errMsg);
        }
        SiteMoreInformation siteMoreInformation = siteMoreInformationRepository.findById(id).orElse(null);
        if (siteMoreInformation == null) {
            String errMsg = String.format("siteMoreInformation with id %s does not exist in system", id);
            throw new SiteMoreInformationNotFoundException(errMsg);
        }
        SiteMoreInformation editedSiteMoreInformation = siteMoreInformationMapper.toEditEntity(siteMoreInformation, siteMoreInformationDto,siteCorrosivityEnvironment);
        SiteMoreInformation persistedSiteMoreInformation = siteMoreInformationRepository.save(editedSiteMoreInformation);
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

    public SiteMoreInformationDto getSiteMoreInformation(Long corrosivityEnvironmentId) {
        SiteMoreInformation siteMoreInformation = siteMoreInformationRepository.findBySiteCorrosivityEnvironment_Id(corrosivityEnvironmentId).orElse(null);
        if (siteMoreInformation == null) {
            String errMsg = String.format("siteMoreInformation with corrosivityEnvironmentId %s does not exist in system", corrosivityEnvironmentId);
            throw new SiteMoreInformationNotFoundException(errMsg);
        }
        return siteMoreInformationMapper.toDto(siteMoreInformation);
    }
}
