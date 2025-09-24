package com.example.transactions.domain.model;

/**
 * Enumeration representing the possible states of a transaction.
 *
 * <p>Transaction lifecycle: PENDING → COMPLETED or FAILED
 */
public enum TransactionStatus {
    /**
     * Transaction has been created but not yet processed.
     */
    PENDING,

    /**
     * Transaction has been successfully completed.
     */
    COMPLETED,

    /**
     * Transaction processing has failed.
     */
    FAILED
}
