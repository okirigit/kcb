package com.okiri_george.kcb.dto;


import lombok.Data;

@Data
public class B2CResponse {

    private boolean success;
    private String message;
    private String transactionId;
    private B2CRequest b2cRequest;

}
