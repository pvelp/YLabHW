package org.example.entity;

import java.util.UUID;

/**
 * Класс описание сущности - Транзакция
 */
public class Transaction {
    /**
     * Id транзакции
     */
    private final UUID id;
    /**
     * Сумма транзакции
     */
    private long amount;
    /**
     * Id владальца транзакции
     */
    private final UUID playerId;

    /**
     * Тип транзакции
     */
    private TransactionType transactionType;

    /**
     * Возвращает id транзакции
     *
     * @return
     */
    public UUID getId() {
        return id;
    }

    /**
     * Вовзвращает сумму транзакции
     *
     * @return
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Устанавилвает сумму транзакции
     *
     * @param amount
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * Возвращает тип транзакции
     *
     * @return
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * Устанавливает тип транзакции
     *
     * @param transactionType
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Вовзращает id владельца транзакции
     *
     * @return
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Преобразует объект в строку
     *
     * @return
     */
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", playerId=" + playerId +
                ", transactionType=" + transactionType +
                '}';
    }

    /**
     * Перечисление типов транзакций
     */
    public enum TransactionType {
        CREDIT,
        DEBIT,
    }

    /**
     * Конструктор
     *
     * @param id
     * @param amount
     * @param playerId
     * @param transactionType
     */
    public Transaction(UUID id, long amount, UUID playerId, TransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.playerId = playerId;
        this.transactionType = transactionType;
    }
}
