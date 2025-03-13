package com.okiri_george.kcb.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

import javax.jms.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okiri_george.kcb.dto.B2CRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;





@Service
@Slf4j
public class PublishRequests {

	private final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	Gson gson;
	
	@Value("${queues.amq-requests}")
	private String finalQueueName;
	

	
	public void publishNewB2CTreansaction(B2CRequest req) throws JsonProcessingException {
		String transactionId = UUID.randomUUID().toString();
		try {
			String transactionDocument = objectMapper.writeValueAsString(req);
			jmsTemplate.setReceiveTimeout(20000);
			jmsTemplate.setMessageIdEnabled(true);
			jmsTemplate.send(finalQueueName, session -> {
				TextMessage message = null;
				try {
					message = (TextMessage) session.createTextMessage(transactionDocument);
					message.setJMSCorrelationID(transactionId);
					message.setJMSExpiration(0);
					message.setJMSMessageID("RequestId");
					message.setJMSDestination((Destination) new ActiveMQQueue(finalQueueName));
					message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
					message.setJMSPriority(Message.DEFAULT_PRIORITY);
					message.setJMSTimestamp(System.nanoTime());
					message.setJMSType("TEXT");
					log.info("TXN ID : {} : ProduceEvents | publishNewReq : smsDocument {}", transactionId,transactionDocument);
				} catch (JMSException e) {
					log.info("TXN ID : {} : ProduceEvents | publishNewReq : Exception {}",transactionId,e.getMessage());
				}
				assert message != null;
				return (jakarta.jms.Message) message;
			});

		} catch (Exception ex) {
			log.info("TXN ID : {} : ProduceEvents | publishNewReq : Exception {}",transactionId,ex.getMessage());
		}
	}

	public void publishNewSmSRequest(String smsDocument, String transactioncode) {

		try {

			jmsTemplate.setReceiveTimeout(20000);
			jmsTemplate.setMessageIdEnabled(true);
			jmsTemplate.send(finalQueueName, session -> {
				TextMessage message = null;
				try {
					message = (TextMessage) session.createTextMessage(smsDocument);
					message.setJMSCorrelationID(transactioncode);
					message.setJMSExpiration(0);
					message.setJMSMessageID("RequestId");
					message.setJMSDestination((Destination) new ActiveMQQueue(finalQueueName));
					message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
					message.setJMSPriority(Message.DEFAULT_PRIORITY);
					message.setJMSTimestamp(System.nanoTime());
					message.setJMSType("TEXT");
					log.info("TXN ID : {} : ProduceEvents | publishNewReq : smsDocument {}", transactioncode,smsDocument);
				} catch (JMSException e) {
					log.info("TXN ID : {} : ProduceEvents | publishNewReq : Exception {}",transactioncode,e.getMessage());
				}
				assert message != null;
				return (jakarta.jms.Message) message;
			});

		} catch (Exception ex) {
			log.info("TXN ID : {} : ProduceEvents | publishNewReq : Exception {}",transactioncode,ex.getMessage());
		}
	}



}


