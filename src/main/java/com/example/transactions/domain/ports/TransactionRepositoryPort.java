package com.example.transactions.domain.ports;

import com.example.transactions.domain.model.Transaction;
import java.util.List;
import java.util.Optional;

/**
 * Port (interface) for transaction repository operations.
 *
 * <p>This interface defines the contract for persisting and retrieving transactions, following
 * hexagonal architecture principles. Implementations are provided in the infrastructure layer.
 */
public interface TransactionRepositoryPort {

    /**
     * Saves a transaction.
     *
     * @param transaction the transaction to save
     * @return the saved transaction
     */
    Transaction save(Transaction transaction);

    /**
     * Finds a transaction by its unique identifier.
     *
     * @param id the transaction ID
     * @return the transaction if found, empty otherwise
     */
    Optional<Transaction> findById(String id);

    /**
     * Finds all transactions with pagination support.
     *
     * @param offset the number of transactions to skip
     * @param limit the maximum number of transactions to return
     * @return a list of transactions
     */
    List<Transaction> findAll(int offset, int limit);

    /**
     * Counts the total number of transactions.
     *
     * @return the total count
     */
    long count();

    /**
     * Checks if a transaction exists with the given ID.
     *
     * @param id the transaction ID
     * @return true if the transaction exists, false otherwise
     */
    boolean existsById(String id);
}
