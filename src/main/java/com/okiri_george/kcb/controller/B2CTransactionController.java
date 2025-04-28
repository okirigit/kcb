package com.okiri_george.kcb.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;
import com.okiri_george.kcb.service.MobileMoneyServiceImpl;

import com.okiri_george.kcb.utils.AmqMessageProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")


public class B2CTransactionController {

    private final AmqMessageProducer amqService;
    private final MobileMoneyServiceImpl mobileMoneyService;

    @Autowired
    public B2CTransactionController(AmqMessageProducer amqService, MobileMoneyServiceImpl mobileMoneyService) {
        this.amqService = amqService;
        this.mobileMoneyService = mobileMoneyService;
    }
    @PostMapping("/b2c/initiate")
    public ResponseEntity<?> initiateB2C(@Valid @RequestBody B2CRequest request) throws JsonProcessingException {
        amqService.sendB2CTransactionMessage(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Success");
    }

    @GetMapping("/transaction/{transactionId}")
    public B2CResponse getTransactionStatus(@PathVariable String transactionId) {
        return mobileMoneyService.getTransactionStatus(transactionId);
    }

    // Callback from the external system (e.g., M-Pesa, Airtel Money)
    @PostMapping("/transaction/callback")
    public ResponseEntity<?> handleTransactionCallback(@RequestBody B2CResponse transactionResponse) {
        mobileMoneyService.handleCallback(transactionResponse);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/transaction/history")
    public List<B2CResponse> getTransactionHistory(
            @RequestParam(required = false) String phoneNumber
            ) {
        return mobileMoneyService.getTransactions(phoneNumber);
    }


    @GetMapping("/health")
    public String healthCheck() {
        return "{\"status\": \"UP\"}";
    }

}
