package org.example.repository.impl;

import org.example.entity.Transaction;
import org.example.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * InMemory реализация интерфейса TransactionRepository
 */
public class InMemTransactionRepositoryImpl implements TransactionRepository {
    /**
     * Список транзакций
     */
    private final List<Transaction> transactionList;

    /**
     * Конструктор
     *
     * @param transactionList
     */
    public InMemTransactionRepositoryImpl(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }


    /**
     * Добавляет новую транзакцию в список
     *
     * @param transaction
     */
    @Override
    public void insert(Transaction transaction) {
        transactionList.add(transaction);
    }

    /**
     * Находит и возвращает транзакцию из списка по ее id
     *
     * @param transactionId
     * @return Transaction | null
     */
    @Override
    public Transaction find(UUID transactionId) {
        for (Transaction transaction : transactionList) {
            if (transaction.getId() == transactionId) {
                return transaction;
            }
        }
        return null;
    }

    /**
     * Возвращает список транзакций
     *
     * @return List
     */
    @Override
    public List<Transaction> findAll() {
        return transactionList;
    }

    /**
     * Обновляет информацию транзакции по ее id
     *
     * @param id
     * @param transaction
     */
    @Override
    public void update(UUID id, Transaction transaction) {
        for (Transaction t : transactionList) {
            if (t.getId() == id) {
                t.setTransactionType(transaction.getTransactionType());
                t.setAmount(transaction.getAmount());
            }
        }
    }

    /**
     * Удаляет транзакцию по ее id
     *
     * @param id
     */
    @Override
    public void delete(UUID id) {
        transactionList.removeIf(transaction -> transaction.getId() == id);
    }

    /**
     * Находит и возвращает транзакции с переданным типом
     *
     * @param type
     * @return List
     */
    @Override
    public List<Transaction> findByTransactionType(Transaction.TransactionType type) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction t : transactionList) {
            if (t.getTransactionType() == type) {
                transactions.add(t);
            }
        }
        return transactions;
    }
}
