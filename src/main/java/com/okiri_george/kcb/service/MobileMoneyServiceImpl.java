package com.okiri_george.kcb.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.repository.B2CResponseRepository;
import com.okiri_george.kcb.repository.B2CTransactionRepository;

import com.okiri_george.kcb.utils.RestTemplateHelper;
import lombok.extern.slf4j.Slf4j;

import com.okiri_george.kcb.dto.B2CResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class MobileMoneyServiceImpl implements MobileMoneyService {


    @Value("${messages.smsSuccessMessage}")
    private String smsMessage;



    private final NotificationServiceImpl notificationService;
    private final RestTemplateHelper restTemplateHelper;


    private final B2CTransactionRepository b2CTransactionRepository;
    private final B2CResponseRepository b2CResponseRepository;

    public MobileMoneyServiceImpl(RestTemplateHelper restTemplateHelper, NotificationServiceImpl notificationService, B2CTransactionRepository b2CTransactionRepository, B2CResponseRepository b2CResponseRepository) {
        this.restTemplateHelper = restTemplateHelper;
        this.notificationService = notificationService;

        this.b2CTransactionRepository = b2CTransactionRepository;
        this.b2CResponseRepository = b2CResponseRepository;
    }

    @Override
    public B2CResponse processTransaction(B2CRequest request) {
        B2CResponse transaction = new B2CResponse();


        try {
            //call Mobile money API and check if transaction is successfull.
            restTemplateHelper.postB2CRequest(request.getPhoneNumber(),request.getAmount(),request.getNarration());
            transaction.setSuccess(true);
            transaction.setMessage("Success");

            log.info("Transaction success. Notification SMS SENT");
        } catch (Exception e) {

            transaction.setSuccess(false);
            transaction.setMessage("Error");
            log.info("Transaction Error. {}", e.getMessage());
        }
        transaction.setTransactionId(String.valueOf(System.currentTimeMillis()));

        try
        {
            b2CTransactionRepository.save(request);
            b2CResponseRepository.save(transaction);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return transaction;
    }

    @Override
    public B2CResponse getTransactionStatus(String transactionId) {
        Optional<B2CResponse> resp = b2CResponseRepository.findByTransactionId(transactionId);
        if(resp.isPresent()){
            return resp.get();
        }else{
            B2CResponse transaction = new B2CResponse();
            transaction.setMessage("The transaction does not exist");
            transaction.setSuccess(false);
            return  transaction;
        }

    }

    @Override
    public void handleCallback(B2CResponse response) {
        log.info("Received B2C Response from: {}", response);
        //Send sms after response from b2c engine
        notificationService.sendSms(response.getRecipientMobileNumber(),smsMessage);
    }

    @Override
    public List<B2CResponse> getTransactions(String phoneNumber) {
        List<B2CResponse> transactions = b2CResponseRepository.findByRecipientMobileNumber(phoneNumber);
        return transactions;
    }
}