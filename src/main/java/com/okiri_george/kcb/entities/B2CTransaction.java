package com.okiri_george.kcb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Entity
public class B2CTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private BigDecimal amount;
    private String reference;
    private TransactionStatus status;
    private String transactionId;


}

enum TransactionStatus {
    PENDING, SUCCESS, FAILED
}