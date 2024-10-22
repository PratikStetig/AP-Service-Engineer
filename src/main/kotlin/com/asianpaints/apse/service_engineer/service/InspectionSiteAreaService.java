package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.SiteArea;
import com.asianpaints.apse.service_engineer.dto.*;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.SiteAreaNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.SiteAreaMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.repository.SiteAreaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InspectionSiteAreaService {

    private final SiteAreaMapper siteAreaMapper;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final SiteAreaRepository siteAreaRepository;

    public InspectionSiteAreaService(SiteAreaMapper siteAreaMapper,
                                     InspectionSiteRepository inspectionSiteRepository,
                                     SiteAreaRepository siteAreaRepository) {

        this.inspectionSiteRepository = inspectionSiteRepository;
        this.siteAreaMapper = siteAreaMapper;
        this.siteAreaRepository = siteAreaRepository;
    }

    public SiteAreaDto createSiteArea(SiteAreaDto siteAreaDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteAreaDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteAreaDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteArea siteArea = siteAreaMapper.toEntity(siteAreaDto, inspectionSite);
        SiteArea persistedSiteArea = siteAreaRepository.save(siteArea);
        return siteAreaMapper.toDto(persistedSiteArea);
    }

    public SiteAreaDto editSiteArea(Long id, SiteAreaDto siteAreaDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(siteAreaDto.getInspectionSiteId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", siteAreaDto.getInspectionSiteId());
            throw new InspectionSiteNotFound(errMsg);
        }
        SiteArea siteArea = siteAreaRepository.findById(id).orElse(null);
        if (siteArea == null) {
            String errMsg = String.format("SiteArea with id %s does not exist in system", id);
            throw new SiteAreaNotFoundException(errMsg);
        }
        SiteArea editedSiteArea = siteAreaMapper.toEditEntity(siteArea, siteAreaDto, inspectionSite);
        SiteArea persistedSiteArea = siteAreaRepository.save(editedSiteArea);
        return siteAreaMapper.toDto(persistedSiteArea);
    }

    public void deleteSiteArea(Long id) {
        SiteArea siteArea = siteAreaRepository.findById(id).orElse(null);
        if (siteArea == null) {
            String errMsg = String.format("SiteArea with id %s does not exist in system", id);
            throw new SiteAreaNotFoundException(errMsg);
        }
        siteAreaRepository.deleteById(id);
    }

    public List<SiteAreaDto> getAllSiteAreaByInspectionId(Long inspectionId) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionId).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionId);
            throw new InspectionSiteNotFound(errMsg);
        }
        List<SiteArea> siteAreas = siteAreaRepository.findByInspectionSiteId(inspectionId);
        return siteAreas.stream()
                .map(siteAreaMapper::toDto)
                .collect(Collectors.toList());
    }


    public SiteAreaWithImagesResponse getSiteAreaWithImages(Long inspectionSiteId) {
        List<Object[]> result = siteAreaRepository.findSiteAreaWithImages(inspectionSiteId);
        return mapResultToResponse(result);
    }

    private SiteAreaWithImagesResponse mapResultToResponse(List<Object[]> result) {
        SiteAreaResponse siteAreaDto = null;
        List<SiteAreaImageDto> images = new ArrayList<>();

        // Loop through the result and extract site area and images
        for (Object[] row : result) {
            // Map siteArea (only once, since it's the same for all rows)
            if (siteAreaDto == null) {
                siteAreaDto = new SiteAreaResponse(
                        ((BigInteger) row[0]).longValue(),  // siteAreaId
                        (String) row[1],                   // area
                        ((BigInteger) row[4]).longValue(),  // inspectionSiteId
                        (String) row[2],                   // coatingCondition
                        (String) row[3],                   // corrosionType
                        (Integer) row[5]                   // rating
                );
            }

            // Add each image to the images list
            SiteAreaImageDto imageDto = new SiteAreaImageDto(
                    ((BigInteger) row[6]).longValue(),    // imageId
                    (String) row[7],                     // imageUrl
                    ((BigInteger) row[8]).longValue(),    // siteAreaId
                    ((Timestamp) row[9]).toLocalDateTime() // uploadedAt
            );
            images.add(imageDto);
        }

        // Return the combined site area and images response
        return new SiteAreaWithImagesResponse(siteAreaDto, images);
    }
}
