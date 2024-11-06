package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.constants.UserRole;
import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteFilter;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteStatus;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteRequest;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteResponse;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.exception.UserNotFoundException;
import com.asianpaints.apse.service_engineer.mapper.InspectionSiteMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import com.asianpaints.apse.service_engineer.specification.InspectionSiteSpecification;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InspectionSiteService {

    private final ApUserService apUserService;
    private final InspectionSiteMapper inspectionSiteMapper;
    private final InspectionSiteRepository inspectionSiteRepository;
    private static final Logger logger = LoggerFactory.getLogger(InspectionSiteService.class);

    public InspectionSiteResponse createInspectionSite(InspectionSiteRequest inspectionSiteRequest) {
        ApUser apUser = apUserService.getUser(inspectionSiteRequest.getConductedBy());
        InspectionSite inspectionSite = inspectionSiteMapper.toEntity(inspectionSiteRequest, apUser);
        InspectionSite savedInspectionSite = inspectionSiteRepository.save(inspectionSite);
        return inspectionSiteMapper.toDto(savedInspectionSite);
    }

    public InspectionSiteResponse editInspectionSite(Long id, InspectionSiteRequest inspectionSiteRequest) {
        ApUser apUser = apUserService.getUser(inspectionSiteRequest.getConductedBy());
        if (apUser == null) {
            String errMsg = String.format("User with id %s does not exist in system", inspectionSiteRequest.getConductedBy());
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

    public InspectionSiteResponse getInspectionSite(Long id) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(id).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", id);
            throw new InspectionSiteNotFound(errMsg);
        }
        return inspectionSiteMapper.toDto(inspectionSite);
    }

    public List<InspectionSiteResponse> getFilteredInspectionSites(InspectionSiteFilter filter) {
        ApUser apUser = apUserService.getUser(filter.getConductedBy());
        if (apUser == null) {
            String errMsg = String.format("User with id %s does not exist in system", filter.getConductedBy());
            throw new UserNotFoundException(errMsg);
        }

        filter.setAdmin(apUser.getUserType().getUserType().equals(UserRole.ADMIN));

        Specification<InspectionSite> spec = InspectionSiteSpecification.getFilteredInspectionSites(filter);
        List<InspectionSite> results = inspectionSiteRepository.findAll(spec);
        return inspectionSiteMapper.toDtoList(results);
    }

    public InspectionSiteResponse submitInspectionSite(Long inspectionSiteId) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionSiteId).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionSiteId);
            throw new InspectionSiteNotFound(errMsg);
        }
        inspectionSite.setStatus(InspectionSiteStatus.Pending);
        inspectionSiteRepository.save(inspectionSite);
        return inspectionSiteMapper.toDto(inspectionSite);
    }
}
