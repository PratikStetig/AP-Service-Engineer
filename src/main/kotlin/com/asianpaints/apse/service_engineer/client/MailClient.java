package com.asianpaints.apse.service_engineer.client;

import com.asianpaints.apse.service_engineer.model.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MailClient {

    @Value("${mail.from}")
    private String mailFrom;
    @Value("${mail.trigger.endpoint.base-url}")
    private String baseUrl;
    private final RestTemplate restTemplate;

    public void sendMail(EmailDetails emailDetails){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("reqTo", emailDetails.getToRecipient());
        formData.add("reqSubject", emailDetails.getSubject());
        formData.add("reqBody", emailDetails.getMsgBody());
        formData.add("reqFrom", mailFrom);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        String serverUrl = String.format("%s/v2/triggerEmail",baseUrl);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(serverUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);

            System.out.println("Hello");
        }  catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
