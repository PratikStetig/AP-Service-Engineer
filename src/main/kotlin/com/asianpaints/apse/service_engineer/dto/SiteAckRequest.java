package com.asianpaints.apse.service_engineer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteAckRequest {
    private String personName;
    private String designation;
}
