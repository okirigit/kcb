package com.okiri_george.kcb.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;
import com.okiri_george.kcb.service.MobileMoneyServiceImpl;
import com.okiri_george.kcb.service.PublishRequests;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class B2CTransactionController {
    private final PublishRequests amqService;
    @PostMapping("/b2c/initiate")
    public ResponseEntity<?> initiateB2C(@Valid @RequestBody B2CRequest request) throws JsonProcessingException {

        amqService.publishNewB2CTreansaction(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Success");
    }

}
