package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.dto.*;
import com.asianpaints.apse.service_engineer.exception.*;
import com.asianpaints.apse.service_engineer.service.*;
import com.asianpaints.apse.service_engineer.validator.InspectionSiteValidator;
import kotlin.jvm.internal.markers.KMutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/inspections")
@RequiredArgsConstructor
public class InspectionSiteController {

    private final InspectionSiteService inspectionSiteService;
    private final InspectionSiteValidator inspectionSiteValidator;
    private final InspectionSiteAckService inspectionSiteAckService;
    private final InspectionSitePreliminaryObservationService inspectionSitePreliminaryObservationService;
    private final InspectionSiteAreaService inspectionSiteAreaService;
    private final SiteCorrosivityEnvironmentService siteCorrosivityEnvironmentService;
    private final SiteMoreInformationService siteMoreInformationService;
    private final CoatingSystemService coatingSystemService;
    private final SiteAreaImageService siteAreaImageService;

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

    @GetMapping("/inspection-site/{id}/summary")
    public ResponseEntity<Object> getInspectionSiteSubmitDetails(@PathVariable Long id) {
        try {
            HashMap<String, Object> responseMap = new HashMap<>();
            responseMap.put("addedArea", inspectionSiteAreaService.getAllSiteAreaByInspectionId(id));
            responseMap.put("preliminaryObservation", inspectionSitePreliminaryObservationService.getSitePreliminaryObservation(id));
            responseMap.put("corrosivityDetails", siteCorrosivityEnvironmentService.getAllCorrosivityEnvironmentByInspectionId(id));
            responseMap.put("coatingSystem", coatingSystemService.getAllCoatingSystemByInspectionId(id));
            return ResponseEntity.ok(responseMap);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/user/{id}")
    public ResponseEntity<Object> getInspectionSiteUser(@PathVariable Long id) {
        try {
            List<InspectionSiteResponse> inspectionSiteResponse = inspectionSiteService.getInspectionSiteConductedBy(id);
            return ResponseEntity.ok(inspectionSiteResponse);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/inspection-site/acknowledgments/")
    public ResponseEntity<Object> createInspectionSiteAcknowledgment(@RequestBody InspectionSiteAckDto inspectionSiteAckDto) {
        try {
            InspectionSiteAckDto createdinspectionSiteAckDto = inspectionSiteAckService.createInspectionSiteAcknowledgement(inspectionSiteAckDto);
            return ResponseEntity.ok(createdinspectionSiteAckDto);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/inspection-site/acknowledgments/{ackId}")
    public ResponseEntity<Object> editInspectionSiteAcknowledgment(@PathVariable Long ackId, @RequestBody InspectionSiteAckDto inspectionSiteAckDto) {
        try {
            InspectionSiteAckDto editedinspectionSiteAckDto = inspectionSiteAckService.editInspectionSiteAcknowledgement(ackId, inspectionSiteAckDto);
            return ResponseEntity.ok(editedinspectionSiteAckDto);
        } catch (InspectionSiteNotFound | InspectionSiteAckNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/inspection-site/acknowledgments/{ackId}")
    public ResponseEntity<Object> deleteInspectionSiteAcknowledgment(@PathVariable Long ackId) {
        try {
            inspectionSiteAckService.deleteInspectionSiteAcknowledgement(ackId);
            return ResponseEntity.noContent().build();
        } catch (InspectionSiteAckNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/{inspectionSiteId}/acknowledgments/")
    public ResponseEntity<Object> getAllAcknowledgmentByInspectionSite(@PathVariable Long inspectionSiteId) {
        try {
            List<InspectionSiteAckDto> inspectionSiteAckDtos = inspectionSiteAckService.getAllAckByInspectionId(inspectionSiteId);
            return ResponseEntity.ok(inspectionSiteAckDtos);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/inspection-site/preliminary-observation/")
    public ResponseEntity<Object> createPreliminaryObservation(@RequestBody SitePreliminaryObservationDto sitePreliminaryObservationDto) {
        try {
            SitePreliminaryObservationDto createdSitePreliminaryObservationDto = inspectionSitePreliminaryObservationService.createSitePreliminaryObservation(sitePreliminaryObservationDto);
            return ResponseEntity.ok(createdSitePreliminaryObservationDto);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/inspection-site/preliminary-observation/{observationId}")
    public ResponseEntity<Object> editPreliminaryObservation(@PathVariable Long observationId, @RequestBody SitePreliminaryObservationDto sitePreliminaryObservationDto) {
        try {
            SitePreliminaryObservationDto editedSitePreliminaryObservationDto = inspectionSitePreliminaryObservationService.editSitePreliminaryObservation(observationId, sitePreliminaryObservationDto);
            return ResponseEntity.ok(editedSitePreliminaryObservationDto);
        } catch (InspectionSiteNotFound | InspectionSitePreliminaryObservationNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/inspection-site/preliminary-observation/{observationId}")
    public ResponseEntity<Object> deletePreliminaryObservation(@PathVariable Long observationId) {
        try {
            inspectionSitePreliminaryObservationService.deleteSitePreliminaryObservation(observationId);
            return ResponseEntity.noContent().build();
        } catch (InspectionSitePreliminaryObservationNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/{inspectionSiteId}/preliminary-observation/")
    public ResponseEntity<Object> getPreliminaryObservationByInspectionSite(@PathVariable Long inspectionSiteId) {
        try {
            SitePreliminaryObservationDto sitePreliminaryObservationDto = inspectionSitePreliminaryObservationService.getSitePreliminaryObservation(inspectionSiteId);
            return ResponseEntity.ok(sitePreliminaryObservationDto);
        } catch (InspectionSitePreliminaryObservationNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/inspection-site/areas/")
    public ResponseEntity<Object> createInspectionSiteArea(@RequestBody SiteAreaDto siteAreaDto) {
        try {
            SiteAreaDto createdSiteAreaDto = inspectionSiteAreaService.createSiteArea(siteAreaDto);
            return ResponseEntity.ok(createdSiteAreaDto);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/inspection-site/areas/{areaId}")
    public ResponseEntity<Object> editInspectionSiteArea(@PathVariable Long areaId, @RequestBody SiteAreaDto siteAreaDto) {
        try {
            SiteAreaDto editedSiteAreaDto = inspectionSiteAreaService.editSiteArea(areaId, siteAreaDto);
            return ResponseEntity.ok(editedSiteAreaDto);
        } catch (InspectionSiteNotFound | SiteAreaNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/inspection-site/areas/{areaId}")
    public ResponseEntity<Object> deleteInspectionSiteArea(@PathVariable Long areaId) {
        try {
            inspectionSiteAreaService.deleteSiteArea(areaId);
            return ResponseEntity.noContent().build();
        } catch (SiteAreaNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/{inspectionSiteId}/areas/")
    public ResponseEntity<Object> getAllAreasByInspectionSite(@PathVariable Long inspectionSiteId) {
        try {
            return ResponseEntity.ok(inspectionSiteAreaService.getAllSiteAreaByInspectionId(inspectionSiteId));
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/inspection-site/areas/corrosivity-environment/")
    public ResponseEntity<Object> createSiteCorrosivityEnvironment(@RequestBody SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        try {
            SiteCorrosivityEnvironmentResponse createdSiteCorrosivityEnvironmentResponse = siteCorrosivityEnvironmentService.createSiteCorrosivityEnvironment(siteCorrosivityEnvironmentDto);
            return ResponseEntity.ok(createdSiteCorrosivityEnvironmentResponse);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/inspection-site/areas/corrosivity-environment/{corrosivityEnvironmetId}")
    public ResponseEntity<Object> editSiteCorrosivityEnvironment(@PathVariable Long corrosivityEnvironmetId, @RequestBody SiteCorrosivityEnvironmentDto siteCorrosivityEnvironmentDto) {
        try {
            SiteCorrosivityEnvironmentResponse createdSiteCorrosivityEnvironmentResponse = siteCorrosivityEnvironmentService.editSiteCorrosivityEnvironment(corrosivityEnvironmetId, siteCorrosivityEnvironmentDto);
            return ResponseEntity.ok(createdSiteCorrosivityEnvironmentResponse);
        } catch (InspectionSiteNotFound | SiteCorrosivityEnvironmentNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @DeleteMapping("/inspection-site/areas/corrosivity-environment/{corrosivityEnvironmetId}")
    public ResponseEntity<Object> deleteSiteCorrosivityEnvironment(@PathVariable Long corrosivityEnvironmetId) {
        try {
            siteCorrosivityEnvironmentService.deleteSiteCorrosivityEnvironmentDto(corrosivityEnvironmetId);
            return ResponseEntity.noContent().build();
        } catch (SiteCorrosivityEnvironmentNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/{inspectionSiteId}/areas/corrosivity-environment/")
    public ResponseEntity<Object> getAllSiteCorrosivityEnvironmentByInspectionId(@PathVariable Long inspectionSiteId) {
        try {
            List<SiteCorrosivityEnvironmentResponse> siteCorrosivityEnvironmentResponses = siteCorrosivityEnvironmentService.getAllCorrosivityEnvironmentByInspectionId(inspectionSiteId);
            return ResponseEntity.ok(siteCorrosivityEnvironmentResponses);
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/inspection-site/areas/corrosivity-environment/more-information/")
    public ResponseEntity<Object> createSiteCorrosivityEnvironmentMoreInformation(@RequestBody SiteMoreInformationDto siteMoreInformationDto) {
        try {
            SiteMoreInformationDto createdSiteMoreInformationDto = siteMoreInformationService.createSiteMoreInformation(siteMoreInformationDto);
            return ResponseEntity.ok(createdSiteMoreInformationDto);
        } catch (SiteCorrosivityEnvironmentNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PutMapping("/inspection-site/areas/corrosivity-environment/more-information/{moreInformationId}")
    public ResponseEntity<Object> editSiteCorrosivityEnvironmentMoreInformation(@PathVariable Long moreInformationId, @RequestBody SiteMoreInformationDto siteMoreInformationDto) {
        try {
            SiteMoreInformationDto editedSiteMoreInformationDto = siteMoreInformationService.editSiteMoreInformation(moreInformationId, siteMoreInformationDto);
            return ResponseEntity.ok(editedSiteMoreInformationDto);
        } catch (SiteCorrosivityEnvironmentNotFoundException | SiteMoreInformationNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @DeleteMapping("/inspection-site/areas/corrosivity-environment/more-information/{moreInformationId}")
    public ResponseEntity<Object> deleteSiteCorrosivityEnvironmentMoreInformation(@PathVariable Long moreInformationId) {
        try {
            siteMoreInformationService.deleteSiteMoreInformation(moreInformationId);
            return ResponseEntity.noContent().build();
        } catch (SiteMoreInformationNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/inspection-site/areas/corrosivity-environment/{corrosivityEnvironmetId}/more-information/")
    public ResponseEntity<Object> getSiteCorrosivityEnvironmentMoreInformation(@PathVariable Long corrosivityEnvironmetId) {
        try {
            SiteMoreInformationDto siteMoreInformationDto = siteMoreInformationService.getSiteMoreInformation(corrosivityEnvironmetId);
            return ResponseEntity.ok(siteMoreInformationDto);
        } catch (SiteMoreInformationNotFoundException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }


    @GetMapping("/inspection-site/{inspectionSiteId}/coating-system")
    public ResponseEntity<Object> getAllInspectionCoatingSystem(@PathVariable Long inspectionSiteId) {
        try {
            // Fetch all coating systems for the given inspection site ID
            return ResponseEntity.ok(coatingSystemService.getAllCoatingSystemByInspectionId(inspectionSiteId));
        } catch (InspectionSiteNotFound ex) {
            // Return a Bad Request response in case the inspection site is not found
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    @GetMapping("/inspection-site/{inspectionSiteId}/site-area/images")
//    public ResponseEntity<Object> getAllInspectionCoatingSystemImages(@PathVariable Long inspectionSiteId) {
//        try {
//            return ResponseEntity.ok(inspectionSiteAreaService.getSiteAreaWithImages(inspectionSiteId));
//        } catch (InspectionSiteNotFound ex) {
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }


    @GetMapping("/inspection-site/{inspectionSiteId}/area/{areaId}/images")
    public ResponseEntity<Object> getInspectionAreaImages(@PathVariable Long inspectionSiteId, @PathVariable Long areaId) {
        try {
            return ResponseEntity.ok(siteAreaImageService.getSiteAreaImage(areaId));
        } catch (InspectionSiteNotFound ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
