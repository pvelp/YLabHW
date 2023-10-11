package org.example.repository;

import org.example.entity.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для работы с репозиторием транзакций
 */
public interface TransactionRepository {
    /**
     * Добавлеяет транзакцию
     *
     * @param transaction
     */
    void insert(Transaction transaction);

    /**
     * Находит транзакцию по ее id
     *
     * @param transactionId
     * @return Transaction | null
     */
    Transaction find(UUID transactionId);

    /**
     * Возвращает список транзакций
     *
     * @return List
     */
    List<Transaction> findAll();

    /**
     * Обновляет инфомрацию транзакции по ее id
     *
     * @param id
     * @param transaction
     */
    void update(UUID id, Transaction transaction);

    /**
     * Удаляет транзакцию по id
     *
     * @param id
     */
    void delete(UUID id);

    /**
     * Возвращает список транзакций в зависимоти от переданного типа
     *
     * @param type
     * @return List
     */
    List<Transaction> findByTransactionType(Transaction.TransactionType type);
}
