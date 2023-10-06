package org.example.repository;

import org.example.entity.Transaction;

import java.util.UUID;

public interface TransactionRepository {
    Transaction insert(Transaction transaction);
    Transaction find(UUID transactionId);
    void update(UUID id, Transaction transaction);
    void delete(UUID id);
}
