package com.okiri_george.kcb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.TextMessage;

import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;
import com.okiri_george.kcb.service.MobileMoneyServiceImpl;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.jms.JMSException;

@Component
@Slf4j
public class MyMessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MobileMoneyServiceImpl mobileMoneyService;

    public MyMessageListener(MobileMoneyServiceImpl mobileMoneyService) {
        this.mobileMoneyService = mobileMoneyService;
    }


    @JmsListener(destination = "B2CTransactions")
    public void receiveMessage(TextMessage message) {
        try {
            String json = message.getText();
            B2CRequest dto = objectMapper.readValue(json, B2CRequest.class);
            mobileMoneyService.processTransaction(
                    dto);
            log.info("Received message: {}", dto);
            processDto(dto);

        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    private void processDto(B2CRequest req) {

        log.info("Processing DTO: {}", req);

    }
}