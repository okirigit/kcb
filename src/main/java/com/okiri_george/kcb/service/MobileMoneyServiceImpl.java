package com.okiri_george.kcb.service;



import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.repository.B2CTransactionRepository;
import com.okiri_george.kcb.repository.TransactionRepository;
import com.okiri_george.kcb.utils.RestTemplateHelper;
import lombok.extern.slf4j.Slf4j;

import com.okiri_george.kcb.dto.B2CResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;



@Service
@Slf4j
public class MobileMoneyServiceImpl implements MobileMoneyService {


    @Value("${messages.smsSuccessMessage}")
    private String smsMessage;



    private final NotificationServiceImpl notificationService;
    private final RestTemplateHelper restTemplateHelper;
    private final TransactionRepository transactionRepository;
    public MobileMoneyServiceImpl(RestTemplateHelper restTemplateHelper, B2CTransactionRepository transactionRepository, NotificationServiceImpl notificationService, TransactionRepository transactionRepository1) {
        this.restTemplateHelper = restTemplateHelper;
        this.notificationService = notificationService;
        this.transactionRepository = transactionRepository1;
    }

    @Override
    public B2CResponse processTransaction(B2CRequest request) {
        B2CResponse transaction = new B2CResponse();

        try {
            //call Mobile money API and check if transaction is successfull.
            restTemplateHelper.postB2CRequest(request.getPhoneNumber(),request.getAmount(),request.getNarration());
            transaction.setSuccess(true);
            transaction.setMessage("Success");
            notificationService.sendSms(request.getPhoneNumber(),smsMessage);

        } catch (Exception e) {

            transaction.setSuccess(false);
            transaction.setMessage("Error");
        }
        transaction.setTransactionId("MPESA-" + System.currentTimeMillis());
        transaction.setB2cRequest(request);

        transactionRepository.save(transaction);
        return transaction;
    }
}