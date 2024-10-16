package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.domain.entity.SiteCorrosivityEnvironment;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentDto;
import com.asianpaints.apse.service_engineer.dto.SiteCorrosivityEnvironmentResponse;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.SiteCorrosivityEnvironmentNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SiteCorrosivityEnvironmentMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.repository.SiteAreaRepository;
import com.asianpaints.apse.service_engineer.repository.SiteCorrosivityEnvironmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteCorrosivityEnvironmentService {

    private final SiteCorrosivityEnvironmentRepository siteCorrosivityEnvironmentRepository;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final SiteCorrosivityEnvironmentMapper siteCorrosivityEnvironmentMapper;
    private final SiteAreaRepository siteAreaRepository;


    public SiteCorrosivityEnvironmentResponse createSiteCorrosivityEnvironment(SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteCorrosivityEnvironmentDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteCorrosivityEnvironmentDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        Set<SiteArea> siteAreas = siteAreaRepository.findByIds(siteCorrosivityEnvironmentDto.getAreaIds());
        for(SiteArea siteArea : siteAreas){
            if(siteArea.getInspectionSite().getId().longValue() != inspectionSite.getId().longValue()){
                String errMsg = String.format("SiteArea with id %s does not part of InspectionSite %s in system", siteArea.getId(),siteCorrosivityEnvironmentDto.getInspectionSiteId());
                throw new InspectionSiteNotFound(errMsg);
            }
        }
        Set<Long> fetchedSiteIds =  siteAreas.stream().map(SiteArea::getId).collect(Collectors.toSet());
        if(!fetchedSiteIds.containsAll(siteCorrosivityEnvironmentDto.getAreaIds())){
            String errMsg = String.format("Not able to fetch provide all areas rom database");
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentMapper.toEntity(siteCorrosivityEnvironmentDto,inspectionSite,siteAreas);
        SiteCorrosivityEnvironment persistedSiteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.save(siteCorrosivityEnvironment);
        return siteCorrosivityEnvironmentMapper.toDto(persistedSiteCorrosivityEnvironment);
    }

    public SiteCorrosivityEnvironmentResponse editSiteCorrosivityEnvironment(Long id, SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteCorrosivityEnvironmentDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteCorrosivityEnvironmentDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        Set<SiteArea> siteAreas = siteAreaRepository.findByIds(siteCorrosivityEnvironmentDto.getAreaIds());
        for(SiteArea siteArea : siteAreas){
            if(siteArea.getInspectionSite().getId().longValue() != inspectionSite.getId().longValue()){
                String errMsg = String.format("SiteArea with id %s does not part of InspectionSite %s in system", siteArea.getId(),siteCorrosivityEnvironmentDto.getInspectionSiteId());
                throw new InspectionSiteNotFound(errMsg);
            }
        }
        Set<Long> fetchedSiteIds =  siteAreas.stream().map(SiteArea::getId).collect(Collectors.toSet());
        if(!fetchedSiteIds.containsAll(siteCorrosivityEnvironmentDto.getAreaIds())){
            String errMsg = String.format("Not able to fetch provide all areas rom database");
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteCorrosivityEnvironment siteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.findById(id).orElse(null);
        if (siteCorrosivityEnvironment == null) {
            String errMsg = String.format("siteCorrosivityEnvironment with id %s does not exist in system", id);
            throw new SiteCorrosivityEnvironmentNotFoundException(errMsg);
        }
        SiteCorrosivityEnvironment editedSiteCorrosivityEnvironment = siteCorrosivityEnvironmentMapper.toEditEntity(siteCorrosivityEnvironment, siteCorrosivityEnvironmentDto,inspectionSite,siteAreas);
        SiteCorrosivityEnvironment persistedSiteCorrosivityEnvironment = siteCorrosivityEnvironmentRepository.save(editedSiteCorrosivityEnvironment);
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

    public List<SiteCorrosivityEnvironmentResponse> getAllCorrosivityEnvironmentByInspectionId(Long inspectionId){
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionId).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionId);
            throw new InspectionSiteNotFound(errMsg);
        }
       List<SiteCorrosivityEnvironment> siteCorrosivityEnvironments = siteCorrosivityEnvironmentRepository.findByInspectionSiteId(inspectionId);
        return siteCorrosivityEnvironments.stream()
                .map(siteCorrosivityEnvironment -> siteCorrosivityEnvironmentMapper.toDto(siteCorrosivityEnvironment))
                .collect(Collectors.toList());
    }

}
