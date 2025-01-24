package com.asianpaints.apse.service_engineer.dto;

import lombok.Data;

@Data
public class FileUploadResponseDto {
    private Object assetData;
    private String message;
    private String assetUrl;
    private String status;
}
