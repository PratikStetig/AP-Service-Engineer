package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.client.FileUploadClient;
import com.asianpaints.apse.service_engineer.dto.AzureFileUploadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadClient fileUploadClient;

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AzureFileUploadResponse> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        ResponseEntity<String> response = fileUploadClient.addFile(file.getOriginalFilename(), bytes);
        ObjectMapper mapper = new ObjectMapper();
        AzureFileUploadResponse azureFileResponse = mapper.readValue(response.getBody(), AzureFileUploadResponse.class);
        return ResponseEntity.ok(azureFileResponse);
    }
}
