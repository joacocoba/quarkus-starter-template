package com.example.transactions.domain.transaction.exceptions;

import com.example.transactions.domain.shared.exceptions.DomainException;

/**
 * Exception thrown when a transaction is not found.
 *
 * <p>This exception is thrown when attempting to retrieve a transaction that does not exist in the
 * system.
 */
public class TransactionNotFoundException extends DomainException {

  public TransactionNotFoundException(String transactionId) {
    super("TRANSACTION_NOT_FOUND", "Transaction not found with ID: " + transactionId);
  }
}
