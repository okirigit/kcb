package com.okiri_george.kcb.service;


import com.okiri_george.kcb.utils.RestTemplateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {


    private final RestTemplateHelper restTemplateHelper;



    public NotificationServiceImpl(RestTemplateHelper restTemplateHelper) {
        this.restTemplateHelper = restTemplateHelper;
    }

    @Override
    public void sendSms(String phoneNumber, String message) {
        List<String> recipients = new ArrayList<>();
        recipients.add(phoneNumber);

        try {
            restTemplateHelper.sendSMS(phoneNumber,message);
         log.info("SMS sent");

        } catch (Exception e) {
            log.error("Error sending SMS to {}: {}", phoneNumber, e.getMessage());
        }
    }
}