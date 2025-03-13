package com.okiri_george.kcb.repository;




import com.okiri_george.kcb.entities.B2CTransaction;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TransactionRepository implements B2CTransactionRepository {
    private final Map<String, B2CTransaction> transactions = new ConcurrentHashMap<>();

    @Override
    public void save(B2CTransaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
    }

    @Override
    public Optional<B2CTransaction> findByTransactionId(String transactionId) {
        return Optional.ofNullable(transactions.get(transactionId));
    }
}
