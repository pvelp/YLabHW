package org.example.service;

import org.example.entity.Player;
import org.example.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface WalletService {
    Player register(String username, String password);

    boolean login(String username, String password);

    String getBalance();

    void transaction(long amount, Transaction.TransactionType transactionType);

    List<Transaction> getAllTransactions();

    List<Transaction> getDebitHistory();

    List<Transaction> getCreditHistory();

    void onExit();

    UUID getSessionId();
}
