package com.okiri_george.kcb.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.repository.B2CTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class AmqMessageProducer {

    private final ObjectMapper mapper = new ObjectMapper();

    private final JmsTemplate jmsTemplate;

    @Value("${spring.activemq.B2CRequestQueue}")
    private String QUEUE_LOANS_REQUEST ;




    public AmqMessageProducer(JmsTemplate jmsTemplate, B2CTransactionRepository b2CTransactionRepository) {
        this.jmsTemplate = jmsTemplate;


    }

    public ResponseEntity<ResponseObject> sendB2CTransactionMessage(B2CRequest request) {
        ResponseObject responseObject = new ResponseObject();
        ResponseEntity<ResponseObject> responseEntity ;

        try {
            String jsonString = mapper.writeValueAsString(request);
            jmsTemplate.convertAndSend(QUEUE_LOANS_REQUEST, jsonString);
            log.info("B2C transaction for  Received from :::::::::::::::: {}", request);
            responseObject.setMessage("B2C transaction   received Successfully.");
            responseObject.setStatus("SUCCESS");
            responseObject.setSuccess(Boolean.TRUE);
            responseEntity = new ResponseEntity<>(responseObject, HttpStatus.OK);
        }  catch (Exception e) {
            responseObject.setStatus("ERROR");
            responseObject.setMessage(e.getMessage());
            responseObject.setSuccess(Boolean.FALSE);
            responseEntity = new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Error while sending B2C transaction message {}", e.getMessage());
        }
        return responseEntity;
    }



}