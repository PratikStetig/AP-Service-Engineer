package com.asianpaints.apse.service_engineer.dto;

import lombok.*;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SiteAreaWithImagesResponse {
    private SiteAreaResponse siteArea;
    private List<SiteAreaImageDto> images;

    // Constructor, Getters, and Setters
}