package org.example.entity;

import java.util.UUID;

public class Transaction {
    private final UUID id;
    private double amount;

    private TransactionType transactionType;

    public UUID getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public enum TransactionType{
        CREDIT,
        DEBIT,
    }
    public Transaction(UUID id, double amount, TransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}
