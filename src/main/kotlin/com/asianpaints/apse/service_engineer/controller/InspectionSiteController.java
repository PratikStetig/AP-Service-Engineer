package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;
import com.asianpaints.apse.service_engineer.dto.SitePreliminaryObservationDto;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteAckNotFoundException;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.InspectionSitePreliminaryObservationNotFoundException;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.service.InspectionSiteAckService;
import com.asianpaints.apse.service_engineer.service.InspectionSitePreliminaryObservationService;
import com.asianpaints.apse.service_engineer.service.InspectionSiteService;
import com.asianpaints.apse.service_engineer.validator.InspectionSiteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inspections")
@RequiredArgsConstructor
public class InspectionSiteController {

    private final InspectionSiteService inspectionSiteService;
    private final InspectionSiteValidator inspectionSiteValidator;
    private final InspectionSiteAckService inspectionSiteAckService;
    private final InspectionSitePreliminaryObservationService inspectionSitePreliminaryObservationService;

    @PostMapping("/inspection-site")
    public ResponseEntity<Object> createInspectionSite(@RequestBody InspectionSiteRequest inspectionSiteRequest) {

        List<String> validationErrors = inspectionSiteValidator.validate(inspectionSiteRequest);
        if (!validationErrors.isEmpty()) {
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            InspectionSiteResponse inspectionSiteResponse = inspectionSiteService.createInspectionSite(inspectionSiteRequest);
            return ResponseEntity.ok(inspectionSiteResponse);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/inspection-site/{id}")
    public ResponseEntity<Object> editInspectionSite(@PathVariable Long id, @RequestBody InspectionSiteRequest inspectionSiteRequest) {

        List<String> validationErrors = inspectionSiteValidator.validate(inspectionSiteRequest);
        if (!validationErrors.isEmpty()) {
            String errorMsg = String.join(",", validationErrors);
            return ResponseEntity.badRequest().body(errorMsg);
        }
        try {
            InspectionSiteResponse inspectionSiteResponse = inspectionSiteService.editInspectionSite(id, inspectionSiteRequest);
            return ResponseEntity.ok(inspectionSiteResponse);
        } catch (UserNotFoundException | InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/{id}")
    public ResponseEntity<Object> getInspectionSite(@PathVariable Long id) {
        try {
            InspectionSiteResponse inspectionSiteResponse = inspectionSiteService.getInspectionSite(id);
            return ResponseEntity.ok(inspectionSiteResponse);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
