package org.example.service.impl;

import org.example.entity.Player;
import org.example.entity.Transaction;
import org.example.repository.PlayerRepository;
import org.example.repository.TransactionRepository;
import org.example.service.WalletService;
import org.example.utils.BalanceConverter;
import org.example.utils.PasswordEncryption;

import java.util.List;
import java.util.UUID;

public class InMemWalletServiceImpl implements WalletService {

    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    public UUID getSessionId() {
        return sessionId;
    }

    private UUID sessionId = null;

    public InMemWalletServiceImpl(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Player register(String username, String password) {
        return playerRepository.insert(
                new Player(UUID.randomUUID(), username, PasswordEncryption.encryptPassword(password), 0L, false)
        );
    }

    @Override
    public boolean login(String username, String password) throws RuntimeException {
        Player player = playerRepository.findByUsername(username);
        if (player == null) {
            throw new RuntimeException("player with username: " + username + " was not found");
        }
        if (player.isAuthenticated()) {
            return true;
        }
        boolean authenticated = player.isPasswordValid(password);
        if (authenticated) {
            player.setAuthenticated(true);
            sessionId = player.getId();
        }
        return authenticated;
    }

    @Override
    public String getBalance() {
        Player player = playerRepository.find(sessionId);
        long balance = player.getBalance();
        return BalanceConverter.convertBalance(balance);
    }

    @Override
    public void transaction(long amount, Transaction.TransactionType transactionType) throws RuntimeException {
        UUID transactionId = UUID.randomUUID();
        List<Transaction> transactionList = transactionRepository.findAll();
        for (Transaction t : transactionList) {
            if (t.getId() == transactionId) {
                throw new RuntimeException("debit transaction with this id already exist");
            }
        }
        Player player = playerRepository.find(sessionId);
        switch (transactionType) {
            case DEBIT -> {
                long balance = player.getBalance();
                if (balance < amount) {
                    throw new RuntimeException("Not enough money");
                }
                player.subBalance(amount);
                break;
            }
            case CREDIT -> {
                player.addBalance(amount);
                break;
            }
        }
        Transaction transaction = new Transaction(transactionId, amount, sessionId, transactionType);
        transactionRepository.insert(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getDebitHistory() {
        return transactionRepository.findByTransactionType(Transaction.TransactionType.DEBIT);
    }

    @Override
    public List<Transaction> getCreditHistory() {
        return transactionRepository.findByTransactionType(Transaction.TransactionType.CREDIT);
    }

    public void onExit() {
        sessionId = null;
    }
}
