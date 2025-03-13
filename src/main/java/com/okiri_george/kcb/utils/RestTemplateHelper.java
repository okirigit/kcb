package com.okiri_george.kcb.utils;


import com.okiri_george.kcb.config.RestTemplateConfig;
import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.SmsRequest;
import com.okiri_george.kcb.entities.B2CTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;





@Component
public class RestTemplateHelper {

    @Value("${mobilemoney.mpesa.api-url}")
    private String url;

    @Autowired
    private RestTemplateConfig restTemplate;

    public ResponseEntity<B2CTransaction> postB2CRequest(String phoneNumber, BigDecimal amount, String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        B2CRequest request = new B2CRequest();
        request.setPhoneNumber(phoneNumber);
        request.setAmount(amount);
        request.setNarration(reference);
        HttpEntity<B2CRequest> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.restTemplate(restTemplate.restTemplateBuilder()).postForEntity(url, requestEntity, B2CTransaction.class);
    }

    public ResponseEntity<String> sendSMS(String phoneNumber, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        SmsRequest request = new SmsRequest();
        request.setPhoneNumber(phoneNumber);
        request.setMessage("Message to be sent");

        HttpEntity<SmsRequest> requestEntity = new HttpEntity<>(request, headers);
        return restTemplate.restTemplate(restTemplate.restTemplateBuilder()).postForEntity(url, requestEntity, String.class);
    }

    public boolean isResponseOk(ResponseEntity<?> response) {
        return response != null && response.getStatusCode() == HttpStatus.OK;
    }

    public <T> T getResponseBody(ResponseEntity<T> response){
        if (isResponseOk(response)){
            return response.getBody();
        }
        return null;
    }

}
