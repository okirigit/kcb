package com.okiri_george.kcb.repository;




import com.okiri_george.kcb.dto.B2CRequest;
import com.okiri_george.kcb.dto.B2CResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface B2CResponseRepository extends JpaRepository<B2CResponse, String> {

    Optional<B2CResponse> findByTransactionId(String transactionId);
    List<B2CResponse> findByRecipientMobileNumber(String phoneNumber);

}
