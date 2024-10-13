package com.asianpaints.apse.service_engineer.controller;

import com.asianpaints.apse.service_engineer.client.FileUploadClient;
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
public class FileUploadController {
    private final FileUploadClient fileUploadClient;

    public FileUploadController(FileUploadClient fileUploadClient){
        this.fileUploadClient = fileUploadClient;
    }

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return fileUploadClient.addFile(file.getOriginalFilename(), bytes);
    }
}
