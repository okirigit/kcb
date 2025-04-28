package com.okiri_george.kcb.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class B2CResponse {
    @Id
    private Long id;

    private boolean success;
    private String message;
    private String transactionId;
    private double amount;
    private String recipientMobileNumber;
    private String timestamp;


}
