package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.SiteChemical;
import com.asianpaints.apse.service_engineer.repository.SiteChemicalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionSitePreliminaryObservationService {

    private final SiteChemicalRepository siteChemicalRepository;

    public InspectionSitePreliminaryObservationService(SiteChemicalRepository siteChemicalRepository){
        this.siteChemicalRepository = siteChemicalRepository;
    }

    public List<SiteChemical> getAllChemicalsExposed(){
        return siteChemicalRepository.findAll();
    }

    public void createSitePreliminaryObservation(){

    }
    public void editSitePreliminaryObservation(){

    }

    public void deleteSitePreliminaryObservation(){

    }

    public void getSitePreliminaryObservation(){

    }
}
