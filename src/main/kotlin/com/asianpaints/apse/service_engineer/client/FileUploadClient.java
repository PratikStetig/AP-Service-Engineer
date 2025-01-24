package com.asianpaints.apse.service_engineer.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FileUploadClient {

    private String url = "https://apidev.asianpaints.com/v1/contentstorage?apikey=jJs5QR9LY5YJcMej3TjnMdXDZ8Air1Zz";

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public ResponseEntity<String> addFile(String fileName, byte[] fileBytesArray) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        long currentTimeMillis = System.currentTimeMillis();

        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("fileData", fileName,
                        RequestBody.create(fileBytesArray, MediaType.parse("application/octet-stream")))
                .addFormDataPart("fileContainer", "aplms")
                .addFormDataPart("fileLocation", "LK2001/" + formattedDateTime + "/" + currentTimeMillis)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return ResponseEntity.status(response.code()).body(response.body() != null ? response.body().string() : "Unknown error");
            } else {
                throw new FileUploadException("File upload failed: " + response.message());
            }
//            ObjectMapper objectMapper = new ObjectMapper();
//            String responseBody = response.body() != null ? response.body().string() : "{}";
//            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);
        }
    }
}
