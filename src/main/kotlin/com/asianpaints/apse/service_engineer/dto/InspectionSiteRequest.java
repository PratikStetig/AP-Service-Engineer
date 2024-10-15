package com.asianpaints.apse.service_engineer.dto;

import com.asianpaints.apse.service_engineer.annotation.DateBeforeOrEqualToday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InspectionSiteRequest {
    @NotBlank(message = "reportName field can't be blank")
    private String reportName;
    @NotBlank(message = "conductedAt field can't be blank")
    private String conductedAt;
    private Long conductedBy;
    @NotBlank(message = "siteId field can't be blank")
    private String siteId;
    @NotBlank(message = "state field can't be blank")
    private String state;
    @NotBlank(message = "city field can't be blank")
    private String city;
    private String imageUrl;
    @NotNull(message = "inspectionDate field can't be null")
    @DateBeforeOrEqualToday
    private LocalDate inspectionDate;
}
