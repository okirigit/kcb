package com.okiri_george.kcb.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;

import java.math.BigDecimal;
import java.util.List;

public interface MobileMoneyService {
    B2CResponse processTransaction(B2CRequest request);
    B2CResponse getTransactionStatus(String transactionId);
    void handleCallback(B2CResponse response);
    List<B2CResponse> getTransactions(String phoneNumber);

}
