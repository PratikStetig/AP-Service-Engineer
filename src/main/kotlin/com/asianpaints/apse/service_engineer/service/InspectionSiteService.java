package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.InspectionSiteMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import org.springframework.stereotype.Service;

@Service
public class InspectionSiteService {

    private final ApUserService apUserService;
    private final InspectionSiteMapper inspectionSiteMapper;
    private final InspectionSiteRepository inspectionSiteRepository;

    public InspectionSiteService(ApUserService apUserService,
                                 InspectionSiteMapper inspectionSiteMapper,
                                 InspectionSiteRepository inspectionSiteRepository) {
        this.apUserService = apUserService;
        this.inspectionSiteMapper = inspectionSiteMapper;
        this.inspectionSiteRepository = inspectionSiteRepository;
    }

    public InspectionSiteResponse createInspectionSite(InspectionSiteRequest inspectionSiteRequest) {
        ApUser apUser = apUserService.getUser(inspectionSiteRequest.getUserId());
        if (apUser == null) {
            String errMsg = String.format("User with id %s does not exist in system", inspectionSiteRequest.getUserId());
            throw new UserNotFoundException(errMsg);
        }
        InspectionSite inspectionSite = inspectionSiteMapper.toEntity(inspectionSiteRequest, apUser);
        InspectionSite savedInspectionSite = inspectionSiteRepository.save(inspectionSite);
        return inspectionSiteMapper.toDto(savedInspectionSite);
    }

    public InspectionSiteResponse editInspectionSite(Long id, InspectionSiteRequest inspectionSiteRequest) {
        ApUser apUser = apUserService.getUser(inspectionSiteRequest.getUserId());
        if (apUser == null) {
            String errMsg = String.format("User with id %s does not exist in system", inspectionSiteRequest.getUserId());
            throw new UserNotFoundException(errMsg);
        }
        InspectionSite inspectionSite = inspectionSiteRepository.findById(id).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", id);
            throw new InspectionSiteNotFound(errMsg);
        }
        InspectionSite editInspectionSite = inspectionSiteMapper.toEditEntity(inspectionSite, inspectionSiteRequest, apUser);
        InspectionSite editedInspectionSite = inspectionSiteRepository.save(editInspectionSite);
        return inspectionSiteMapper.toDto(editedInspectionSite);
    }

    public InspectionSiteResponse getInspectionSite(Long id, InspectionSiteRequest inspectionSiteRequest) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(id).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", id);
            throw new InspectionSiteNotFound(errMsg);
        }
        return inspectionSiteMapper.toDto(inspectionSite);
    }
}
