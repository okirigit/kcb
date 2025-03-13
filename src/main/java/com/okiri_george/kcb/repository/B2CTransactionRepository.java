package com.okiri_george.kcb.repository;




import com.okiri_george.kcb.dto.B2CResponse;
import com.okiri_george.kcb.entities.B2CTransaction;

import java.util.Optional;

public interface B2CTransactionRepository {
    void save(B2CResponse transaction);
    Optional<B2CResponse> findByTransactionId(String transactionId);
}
