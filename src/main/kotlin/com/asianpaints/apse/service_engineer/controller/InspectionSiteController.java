package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;
import com.asianpaints.apse.service_engineer.service.InspectionSiteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/inspection")
public class InspectionSiteController {

    private final InspectionSiteService inspectionSiteService;

    public InspectionSiteController(InspectionSiteService inspectionSiteService){
        this.inspectionSiteService = inspectionSiteService;
    }

    @PostMapping("site")
    public ResponseEntity<Object> createInspectionSite(@RequestBody InspectionSiteRequest inspectionSiteRequest){
            InspectionSiteResponse inspectionSiteResponse = inspectionSiteService.createInspectionSite(inspectionSiteRequest);
            return ResponseEntity.ok(inspectionSiteResponse);

    }
}
