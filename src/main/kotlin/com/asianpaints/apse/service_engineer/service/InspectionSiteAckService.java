package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.InspectionSite;
import com.asianpaints.apse.service_engineer.domain.entity.InspectionSiteAcknowledgement;
import com.asianpaints.apse.service_engineer.dto.InspectionSiteAckDto;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteAckNotFoundException;
import com.asianpaints.apse.service_engineer.exception.InspectionSiteNotFound;
import com.asianpaints.apse.service_engineer.mapper.InspectionSiteAcknowledgementMapper;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteAcknowledgmentRepository;
import com.asianpaints.apse.service_engineer.repository.InspectionSiteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InspectionSiteAckService {

    private final InspectionSiteAcknowledgementMapper inspectionSiteAcknowledgementMapper;
    private final InspectionSiteRepository inspectionSiteRepository;
    private final InspectionSiteAcknowledgmentRepository inspectionSiteAcknowledgmentRepository;

    public InspectionSiteAckService(InspectionSiteAcknowledgementMapper inspectionSiteAcknowledgementMapper,
                                    InspectionSiteRepository inspectionSiteRepository,
                                    InspectionSiteAcknowledgmentRepository inspectionSiteAcknowledgmentRepository) {
        this.inspectionSiteRepository = inspectionSiteRepository;
        this.inspectionSiteAcknowledgementMapper = inspectionSiteAcknowledgementMapper;
        this.inspectionSiteAcknowledgmentRepository = inspectionSiteAcknowledgmentRepository;
    }

    public InspectionSiteAckDto createInspectionSiteAcknowledgement(InspectionSiteAckDto inspectionSiteAckDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionSiteAckDto.getInspectionId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionSiteAckDto.getInspectionId());
            throw new InspectionSiteNotFound(errMsg);
        }
        InspectionSiteAcknowledgement inspectionSiteAcknowledgement = inspectionSiteAcknowledgementMapper.toEntity(inspectionSiteAckDto, inspectionSite);
        InspectionSiteAcknowledgement savedInspectionSiteAcknowledgement = inspectionSiteAcknowledgmentRepository.save(inspectionSiteAcknowledgement);
        return inspectionSiteAcknowledgementMapper.toDto(savedInspectionSiteAcknowledgement);
    }

    public InspectionSiteAckDto editInspectionSiteAcknowledgement(Long id, InspectionSiteAckDto inspectionSiteAckDto) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionSiteAckDto.getInspectionId()).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionSiteAckDto.getInspectionId());
            throw new InspectionSiteNotFound(errMsg);
        }
        InspectionSiteAcknowledgement inspectionSiteAcknowledgement = inspectionSiteAcknowledgmentRepository.findById(id).orElse(null);
        if (inspectionSiteAcknowledgement == null) {
            String errMsg = String.format("InspectionSiteAcknowledgement with id %s does not exist in system", id);
            throw new InspectionSiteAckNotFoundException(errMsg);
        }
        InspectionSiteAcknowledgement editedInspectionSiteAcknowledgement = inspectionSiteAcknowledgementMapper.toEditEntity(inspectionSiteAcknowledgement,
                inspectionSiteAckDto,
                inspectionSite);
        InspectionSiteAcknowledgement savedInspectionSiteAcknowledgement = inspectionSiteAcknowledgmentRepository.save(editedInspectionSiteAcknowledgement);
        return inspectionSiteAcknowledgementMapper.toDto(savedInspectionSiteAcknowledgement);
    }

    public void deleteInspectionSiteAcknowledgement(Long id) {
        InspectionSiteAcknowledgement inspectionSiteAcknowledgement = inspectionSiteAcknowledgmentRepository.findById(id).orElse(null);
        if (inspectionSiteAcknowledgement == null) {
            String errMsg = String.format("InspectionSiteAcknowledgement with id %s does not exist in system", id);
            throw new InspectionSiteAckNotFoundException(errMsg);
        }
        inspectionSiteAcknowledgmentRepository.deleteById(id);
    }

    public List<InspectionSiteAckDto> getAllAckByInspectionId(Long inspectionId) {
        InspectionSite inspectionSite = inspectionSiteRepository.findById(inspectionId).orElse(null);
        if (inspectionSite == null) {
            String errMsg = String.format("InspectionSite with id %s does not exist in system", inspectionId);
            throw new InspectionSiteNotFound(errMsg);
        }
        List<InspectionSiteAcknowledgement> inspectionSiteAcknowledgements = inspectionSiteAcknowledgmentRepository.findByInspectionSiteId(inspectionId);
        return inspectionSiteAcknowledgements.stream()
                .map(inspectionSiteAcknowledgementMapper::toDto)
                .collect(Collectors.toList());
    }

}
