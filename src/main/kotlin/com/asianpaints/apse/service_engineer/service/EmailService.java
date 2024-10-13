package com.asianpaints.apse.service_engineer.service;


import com.asianpaints.apse.service_engineer.client.MailClient;
import com.asianpaints.apse.service_engineer.model.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private MailClient mailClient;

    public EmailService(MailClient mailClient){
        this.mailClient = mailClient;
    }

    public void sendMail(EmailDetails emailDetails) {
        mailClient.sendMail(emailDetails);
    }
}
