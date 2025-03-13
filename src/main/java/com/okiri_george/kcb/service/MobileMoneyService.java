package com.okiri_george.kcb.service;

import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;

import java.math.BigDecimal;

public interface MobileMoneyService {
    B2CResponse processTransaction(B2CRequest request);
}
