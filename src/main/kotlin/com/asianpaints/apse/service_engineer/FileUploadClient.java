package com.asianpaints.apse.service_engineer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploadClient {

    public static void main(String[] args) {
        String urlString = "http://unified-collection-mobile-app.us-east-1.elasticbeanstalk.com/api/v1/file/upload/";
        String filePath = "/Users/pratik/Downloads/SampleJPGImage_2mbmb.jpg";

        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            return;
        }

        String boundary = "---BoundaryString";
        String lineSeparator = "\r\n";

        try {
            System.out.println("Opening connection to URL: " + urlString);
            // Open connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            System.out.println("Connection opened successfully.");

            // Write the request body
            try (OutputStream outputStream = connection.getOutputStream()) {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);

                System.out.println("Writing file part to request body.");
                // Add file part
                writer.append("--" + boundary).append(lineSeparator);
                writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"").append(lineSeparator);
                writer.append("Content-Type: application/octet-stream").append(lineSeparator);
                writer.append(lineSeparator).flush();

                // Write file content
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    outputStream.flush();
                }

                System.out.println("File part written successfully.");

                // End of multipart request
                writer.append(lineSeparator).flush();
                writer.append("--" + boundary + "--").append(lineSeparator).flush();
            }

            System.out.println("Request body written. Sending request...");

            // Get response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Reading response from server...");
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                System.out.println("Response read successfully.");
            } else {
                System.err.println("Server returned non-OK status: " + responseCode);
            }

        } catch (IOException e) {
            System.err.println("An error occurred during the file upload process.");
            e.printStackTrace();
        }
    }
}
