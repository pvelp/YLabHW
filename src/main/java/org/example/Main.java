package org.example;


import org.example.entity.Player;
import org.example.entity.Transaction;
import org.example.in.WalletController;
import org.example.repository.PlayerRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.impl.InMemPlayerRepositoryImpl;
import org.example.repository.impl.InMemTransactionRepositoryImpl;
import org.example.service.WalletService;
import org.example.service.impl.InMemWalletServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Player> players = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();

        PlayerRepository playerRepository = new InMemPlayerRepositoryImpl(players);
        TransactionRepository transactionRepository = new InMemTransactionRepositoryImpl(transactions);

        WalletService walletService = new InMemWalletServiceImpl(playerRepository, transactionRepository);

        WalletController controller = new WalletController(walletService);

        controller.event_loop();
    }
}