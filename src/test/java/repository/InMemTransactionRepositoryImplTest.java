package repository;

import org.example.entity.Transaction;
import org.example.repository.TransactionRepository;
import org.example.repository.impl.InMemTransactionRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InMemTransactionRepositoryImplTest {

    private TransactionRepository repository;
    private List<Transaction> transactions;

    @BeforeEach
    public void setup() {
        transactions = new ArrayList<>();
        repository = new InMemTransactionRepositoryImpl(transactions);
    }

    @Test
    public void testInsertShouldAddTransactionToList() {
        // Arrange
        Transaction transaction = new Transaction(UUID.randomUUID(), 10L, UUID.randomUUID(), Transaction.TransactionType.DEBIT);;

        // Act
        repository.insert(transaction);

        // Assert
        assertTrue(transactions.contains(transaction));
    }

    @Test
    public void testFindShouldReturnTransactionWhenTransactionExists() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction(transactionId, 0L, UUID.randomUUID(), Transaction.TransactionType.DEBIT);
        transactions.add(transaction);

        // Act
        Transaction foundTransaction = repository.find(transactionId);

        // Assert
        assertNotNull(foundTransaction);
        assertEquals(transaction, foundTransaction);
    }

    @Test
    public void testFindShouldReturnNullWhenTransactionDoesNotExist() {
        // Arrange
        UUID transactionId = UUID.randomUUID();

        // Act
        Transaction foundTransaction = repository.find(transactionId);

        // Assert
        assertNull(foundTransaction);
    }

    @Test
    public void testFindAllShouldReturnAllTransactions() {
        // Arrange
        Transaction transaction1 = new Transaction(UUID.randomUUID(), 110L, UUID.randomUUID(), Transaction.TransactionType.DEBIT);
        Transaction transaction2 = new Transaction(UUID.randomUUID(), 10L, UUID.randomUUID(), Transaction.TransactionType.CREDIT);
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Act
        List<Transaction> transactionList = repository.findAll();

        // Assert
        assertEquals(transactions.size(), transactionList.size());
        assertTrue(transactionList.contains(transaction1));
        assertTrue(transactionList.contains(transaction2));
    }

    @Test
    public void testUpdateShouldUpdateTransactionInfo() {
        // Arrange
        Transaction transaction = new Transaction(UUID.randomUUID(), 120L, UUID.randomUUID(), Transaction.TransactionType.DEBIT);
        transactions.add(transaction);

        Transaction updatedTransaction = new Transaction(UUID.randomUUID(), 10L, UUID.randomUUID(), Transaction.TransactionType.CREDIT);;

        // Act
        repository.update(transaction.getId(), updatedTransaction);

        // Assert
        assertEquals(updatedTransaction.getTransactionType(), transaction.getTransactionType());
        assertEquals(updatedTransaction.getAmount(), transaction.getAmount());
    }

    @Test
    public void testDeleteShouldRemoveTransactionFromList() {
        // Arrange
        Transaction transaction = new Transaction(UUID.randomUUID(), 100L, UUID.randomUUID(), Transaction.TransactionType.DEBIT);
        transactions.add(transaction);

        // Act
        repository.delete(transaction.getId());

        // Assert
        assertFalse(transactions.contains(transaction));
    }

    @Test
    public void testFindByTransactionTypeShouldReturnTransactionsWithMatchingType() {
        // Arrange
        Transaction transaction1 = new Transaction(UUID.randomUUID(), 40L, UUID.randomUUID(), Transaction.TransactionType.DEBIT);
        Transaction transaction2 = new Transaction(UUID.randomUUID(), 20L, UUID.randomUUID(), Transaction.TransactionType.CREDIT);
        transactions.add(transaction1);
        transactions.add(transaction2);

        // Act
        List<Transaction> debitTransactions = repository.findByTransactionType(Transaction.TransactionType.DEBIT);
        List<Transaction> creditTransactions = repository.findByTransactionType(Transaction.TransactionType.CREDIT);

        // Assert
        assertEquals(1, debitTransactions.size());
        assertEquals(1, creditTransactions.size());
        assertTrue(debitTransactions.contains(transaction1));
        assertTrue(creditTransactions.contains(transaction2));
    }

}
