package com.asianpaints.apse.service_engineer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditInspectionSiteAckRequest {
    private Long inspectionId;
    private LocalDate inspectionDate;
    private List<InspectionSiteAckDto> acknowledgements;
}
