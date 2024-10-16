package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.domain.entity.SiteChemical;
import com.asianpaints.apse.service_engineer.domain.entity.Zone;
import com.asianpaints.apse.service_engineer.service.InspectionSitePreliminaryObservationService;
import com.asianpaints.apse.service_engineer.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/chemicals")
@RequiredArgsConstructor
public class SiteChemicalController {

    private final InspectionSitePreliminaryObservationService inspectionSitePreliminaryObservationService;

    @GetMapping()
    public ResponseEntity<Object> getAllChemicals(){
        List<SiteChemical> siteChemicals = inspectionSitePreliminaryObservationService.getAllChemicalsExposed();
        return ResponseEntity.ok(siteChemicals);
    }

}
