package com.okiri_george.kcb.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.repository.B2CTransactionRepository;
import com.okiri_george.kcb.service.MobileMoneyServiceImpl;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import java.util.HashMap;

@Component
@Slf4j
public class AmqMessageConsumer {

    private final B2CTransactionRepository b2CTransactionRepository;
    private final ObjectMapper objectMapper;

    private final AmqMessageProducer amqMessageProducer;
    private final MobileMoneyServiceImpl mobileMoneyServiceImpl;




    public AmqMessageConsumer(B2CTransactionRepository b2CTransactionRepository, ObjectMapper objectMapper, AmqMessageProducer amqMessageProducer,  MobileMoneyServiceImpl mobileMoneyServiceImpl) {
        this.b2CTransactionRepository = b2CTransactionRepository;
        this.objectMapper = objectMapper;

        this.amqMessageProducer = amqMessageProducer;
        this.mobileMoneyServiceImpl = mobileMoneyServiceImpl;
    }

    @JmsListener(destination = "${spring.activemq.B2CRequestQueue}")
    public void handleLoanApplication(Message message) throws JMSException, JsonProcessingException {
        try {
            B2CRequest request = objectMapper.readValue(message.getBody(Object.class).toString(), B2CRequest.class);
            mobileMoneyServiceImpl.processTransaction(request);
            log.info("Received B2C Transaction Request from: {}", request.getPhoneNumber());

        } catch (Exception e) {
            log.info("Error processing Loan Application {} ", e.getMessage());
        }
    }


    @Bean
    public ErrorHandler myJmsErrorHandler() {
        return new MyJmsErrorHandler();
    }
}
