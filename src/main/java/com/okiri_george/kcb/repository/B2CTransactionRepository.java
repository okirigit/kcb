package com.okiri_george.kcb.repository;




import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;
import com.okiri_george.kcb.entities.B2CTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface B2CTransactionRepository extends JpaRepository<B2CRequest, Long> {

    Optional<B2CRequest> findByTransactionId(String transactionId);
}
