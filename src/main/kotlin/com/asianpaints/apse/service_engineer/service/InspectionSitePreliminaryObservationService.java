package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteChemical;
import com.asianpaints.apse.service_engineer.domain.entity.SitePreliminaryObservation;
import com.asianpaints.apse.service_engineer.dto.SitePreliminaryObservationDto;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.InspectionSitePreliminaryObservationNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SitePreliminaryObservationMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.repository.SiteChemicalRepository;
import com.asianpaints.apse.service_engineer.repository.SitePreliminaryObservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionSitePreliminaryObservationService {

    private final SiteChemicalRepository siteChemicalRepository;
    private final SitePreliminaryObservationRepository sitePreliminaryObservationRepository;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final SitePreliminaryObservationMapper sitePreliminaryObservationMapper;

    public InspectionSitePreliminaryObservationService(SiteChemicalRepository siteChemicalRepository,
                                                       SitePreliminaryObservationRepository sitePreliminaryObservationRepository,
                                                       InspectionSiteRepository inspectionSiteRepository,
                                                       SitePreliminaryObservationMapper sitePreliminaryObservationMapper){
        this.siteChemicalRepository = siteChemicalRepository;
        this.sitePreliminaryObservationRepository = sitePreliminaryObservationRepository;
        this.inspectionSiteRepository = inspectionSiteRepository;
        this.sitePreliminaryObservationMapper = sitePreliminaryObservationMapper;
    }

    public List<SiteChemical> getAllChemicalsExposed(){
        return siteChemicalRepository.findAll();
    }

    public SitePreliminaryObservationDto createSitePreliminaryObservation(SitePreliminaryObservationDto sitePreliminaryObservationDto){
        InspectionSite inspectionSite = inspectionSiteRepository.findById(sitePreliminaryObservationDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", sitePreliminaryObservationDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
       SitePreliminaryObservation sitePreliminaryObservation = sitePreliminaryObservationMapper.toEntity(sitePreliminaryObservationDto, inspectionSite);
       SitePreliminaryObservation persistedSitePreliminaryObservation = sitePreliminaryObservationRepository.save(sitePreliminaryObservation);
       return sitePreliminaryObservationMapper.toDto(persistedSitePreliminaryObservation);
    }
    public SitePreliminaryObservationDto editSitePreliminaryObservation(Long id, SitePreliminaryObservationDto sitePreliminaryObservationDto){
        InspectionSite inspectionSite = inspectionSiteRepository.findById(sitePreliminaryObservationDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", sitePreliminaryObservationDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SitePreliminaryObservation sitePreliminaryObservation = sitePreliminaryObservationRepository.findById(id).orElse(null);
        if (sitePreliminaryObservation == null) {
            String errMsg = String.format("sitePreliminaryObservation with id %s does not exist in system", id);
            throw new InspectionSitePreliminaryObservationNotFoundException(errMsg);
        }
        SitePreliminaryObservation editedSitePreliminaryObservation = sitePreliminaryObservationMapper.toEditEntity(sitePreliminaryObservation, sitePreliminaryObservationDto, inspectionSite);
        SitePreliminaryObservation persistedSitePreliminaryObservation = sitePreliminaryObservationRepository.save(editedSitePreliminaryObservation);
        return sitePreliminaryObservationMapper.toDto(persistedSitePreliminaryObservation);
    }

    public void deleteSitePreliminaryObservation(Long id){
        SitePreliminaryObservation sitePreliminaryObservation = sitePreliminaryObservationRepository.findById(id).orElse(null);
        if (sitePreliminaryObservation == null) {
            String errMsg = String.format("sitePreliminaryObservation with id %s does not exist in system", id);
            throw new InspectionSitePreliminaryObservationNotFoundException(errMsg);
        }
        sitePreliminaryObservationRepository.deleteById(id);
    }

    public SitePreliminaryObservationDto getSitePreliminaryObservation(Long inspectionSiteId){
        SitePreliminaryObservation sitePreliminaryObservation = sitePreliminaryObservationRepository.findByInspectionSiteId(inspectionSiteId).orElse(null);
        if (sitePreliminaryObservation == null) {
            String errMsg = String.format("sitePreliminaryObservation with inspectionSiteId %s does not exist in system", inspectionSiteId);
            throw new InspectionSitePreliminaryObservationNotFoundException(errMsg);
        }
        return sitePreliminaryObservationMapper.toDto(sitePreliminaryObservation);
    }
}
