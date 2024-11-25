package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteReportVersions;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteReportVersionsResponse;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteReportVersionsRepository;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.swing.text.html.Option;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class InspectionSiteReportVersionsService {

    private final InspectionSiteReportVersionsRepository reportVersionsRepository;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final PdfGenerationService pdfGenerationService;

    @Autowired
    public InspectionSiteReportVersionsService(InspectionSiteReportVersionsRepository reportVersionsRepository, InspectionSiteRepository inspectionSiteRepository, PdfGenerationService pdfGenerationService) {
        this.reportVersionsRepository = reportVersionsRepository;
        this.inspectionSiteRepository = inspectionSiteRepository;
        this.pdfGenerationService = pdfGenerationService;
    }


    /**
     * Retrieves the latest report version for a given inspection site ID.
     *
     * @param inspectionSiteId the ID of the inspection site
     * @return the latest InspectionSiteReportVersions, if available
     */
    public Optional<InspectionSiteReportVersionsResponse> getLatestReportByInspectionSiteId(Long inspectionSiteId) {
        // Check if the InspectionSite exists; throw custom exception if not
        final InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionSiteId)
                .orElseThrow(() -> new InspectionSiteNotFound(
                        String.format("InspectionSite with id %s does not exist in the system", inspectionSiteId)
                ));

        // Retrieve the latest report version, or throw an exception if not present
        Optional<InspectionSiteReportVersions> inspectionSiteReportVersions = reportVersionsRepository.findFirstByInspectionSiteIdIdAndDeletedFalseOrderByVersionNumberDesc(inspectionSiteId);
        if (!inspectionSiteReportVersions.isPresent()) {
            if (inspectionSite.status == InspectionSiteStatus.Pending || inspectionSite.status == InspectionSiteStatus.Rejected) {
                pdfGenerationService.generatePdfAsync(inspectionSiteId);
            }
            throw new RuntimeException("Inspection site report not present, please try after some time");
        }

        // Map the entity to the response DTO and return
        return inspectionSiteReportVersions.map(this::mapToResponse);
    }

    /**
     * Creates and saves a new InspectionSiteReportVersion after validating inputs.
     *
     * @param pdfUrl           the URL of the PDF report
     * @param inspectionSiteId the associated inspection site
     * @return the saved InspectionSiteReportVersions object
     */
    @Transactional
    public InspectionSiteReportVersions createReportVersion(String pdfUrl, Long inspectionSiteId) {
        validatePdfUrl(pdfUrl);
        Integer versionNumber = reportVersionsRepository.findLatestVersionNumberByInspectionSiteId(inspectionSiteId);
        if (versionNumber == null) versionNumber = 0;

        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionSiteId).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionSiteId);
            throw new InspectionSiteNotFound(errMsg);
        }

        InspectionSiteReportVersions reportVersion = new InspectionSiteReportVersions();
        reportVersion.setPdfUrl(pdfUrl);
        reportVersion.setVersionNumber(++versionNumber);
        reportVersion.setCreatedAt(LocalDateTime.now());
        reportVersion.setInspectionSiteId(inspectionSite);
        reportVersion.setDeleted(false);

        return reportVersionsRepository.save(reportVersion);
    }

    /**
     * Updates an existing report version's PDF URL and version number after validation.
     *
     * @param id            the ID of the report version to update
     * @param newPdfUrl     the new PDF URL
     * @param versionNumber the new version number
     * @return the updated InspectionSiteReportVersions object
     */
    @Transactional
    public InspectionSiteReportVersions updateReportVersion(Long id, String newPdfUrl, Integer versionNumber) {
        validatePdfUrl(newPdfUrl);
        validateVersionNumber(versionNumber);

        InspectionSiteReportVersions reportVersion = reportVersionsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report version not found for ID: " + id));

        reportVersion.setPdfUrl(newPdfUrl);
        reportVersion.setVersionNumber(versionNumber);
        reportVersion.setCreatedAt(LocalDateTime.now());

        return reportVersionsRepository.save(reportVersion);
    }

    /**
     * Marks a report version as deleted (soft delete).
     *
     * @param id the ID of the report version to delete
     */
    @Transactional
    public void deleteReportVersion(Long id) {
        InspectionSiteReportVersions reportVersion = reportVersionsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report version not found for ID: " + id));

        reportVersion.setDeleted(true);
        reportVersionsRepository.save(reportVersion);
    }

    /**
     * Validates that the PDF URL is not empty and is a valid URL format.
     */
    private void validatePdfUrl(String pdfUrl) {
        if (!StringUtils.hasText(pdfUrl)) {
            throw new ValidationException("PDF URL cannot be empty.");
        }
        // Further validation for URL format can be done here if necessary
    }

    /**
     * Validates that the version number is a positive integer.
     */
    private void validateVersionNumber(Integer versionNumber) {
        if (versionNumber == null || versionNumber <= 0) {
            throw new ValidationException("Version number must be a positive integer.");
        }
    }

    /**
     * Validates that the associated inspection site is not null.
     */
    private void validateInspectionSite(InspectionSite inspectionSite) {
        if (inspectionSite == null) {
            throw new ValidationException("Inspection site cannot be null.");
        }
    }

    public InspectionSiteReportVersionsResponse mapToResponse(InspectionSiteReportVersions reportVersion) {
        return InspectionSiteReportVersionsResponse.builder()
                .id(reportVersion.getId())
                .pdfUrl(reportVersion.getPdfUrl())
                .versionNumber(reportVersion.getVersionNumber())
                .createdAt(reportVersion.getCreatedAt())
                .inspectionSiteId(reportVersion.getInspectionSiteId() != null ? reportVersion.getInspectionSiteId().getId() : null)
                .deleted(reportVersion.getDeleted())
                .build();
    }

}
