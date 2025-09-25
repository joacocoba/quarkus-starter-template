package com.example.transactions.application.transaction.usecases;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.example.transactions.domain.transaction.exceptions.TransactionNotFoundException;
import com.example.transactions.domain.transaction.model.Transaction;
import com.example.transactions.domain.transaction.ports.TransactionRepositoryPort;

/**
 * Use case for retrieving a transaction by its ID.
 *
 * <p>This class handles the retrieval of a specific transaction, throwing a domain exception if the
 * transaction is not found.
 */
@ApplicationScoped
public class GetTransactionUseCase {

  private final TransactionRepositoryPort transactionRepository;

  @Inject
  public GetTransactionUseCase(TransactionRepositoryPort transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  /**
   * Retrieves a transaction by its unique identifier.
   *
   * @param transactionId the transaction ID
   * @return the transaction
   * @throws TransactionNotFoundException if the transaction is not found
   */
  public Transaction execute(String transactionId) {
    Optional<Transaction> transaction = transactionRepository.findById(transactionId);

    return transaction.orElseThrow(() -> new TransactionNotFoundException(transactionId));
  }
}
