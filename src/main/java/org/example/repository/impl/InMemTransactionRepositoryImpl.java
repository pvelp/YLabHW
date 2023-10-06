package org.example.repository.impl;

import org.example.entity.Transaction;
import org.example.repository.TransactionRepository;

import java.util.List;
import java.util.UUID;

public class InMemTransactionRepositoryImpl implements TransactionRepository {
    private final List<Transaction> transactionList;

    public InMemTransactionRepositoryImpl(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }


    @Override
    public Transaction insert(Transaction transaction) {
        transactionList.add(transaction);
        return transaction;
    }

    @Override
    public Transaction find(UUID transactionId) {
        for (Transaction transaction : transactionList){
            if (transaction.getId() == transactionId){
                return transaction;
            }
        }
        return null;
    }

    @Override
    public void update(UUID id, Transaction transaction) {
        for (Transaction t : transactionList){
            if (t.getId() == id){
                t.setTransactionType(transaction.getTransactionType());
                t.setAmount(transaction.getAmount());
            }
        }
    }

    @Override
    public void delete(UUID id) {
        transactionList.removeIf(transaction -> transaction.getId() == id);
    }
}
