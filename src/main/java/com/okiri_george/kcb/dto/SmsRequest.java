package com.okiri_george.kcb.dto;


import lombok.Data;

@Data
public class SmsRequest {

    private String phoneNumber;
    private String message;
}
