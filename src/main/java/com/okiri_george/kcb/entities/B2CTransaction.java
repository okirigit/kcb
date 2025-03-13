package com.okiri_george.kcb.entities;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Data
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