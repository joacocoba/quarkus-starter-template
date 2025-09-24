package com.example.transactions.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.example.transactions.domain.model.Transaction;
import com.example.transactions.domain.ports.IdGeneratorPort;
import com.example.transactions.domain.ports.TransactionRepositoryPort;

/**
 * Use case for creating new transactions.
 *
 * <p>This class orchestrates the creation of a new transaction by generating an ID, creating the
 * domain entity, and persisting it.
 */
@ApplicationScoped
public class CreateTransactionUseCase {

  private final TransactionRepositoryPort transactionRepository;
  private final IdGeneratorPort idGenerator;

  @Inject
  public CreateTransactionUseCase(
      TransactionRepositoryPort transactionRepository, IdGeneratorPort idGenerator) {
    this.transactionRepository = transactionRepository;
    this.idGenerator = idGenerator;
  }

  /**
   * Creates a new transaction with the specified amount, currency, and account numbers.
   *
   * @param amount the transaction amount
   * @param currency the currency code
   * @param originAccountNumber the origin account number
   * @param destinationAccountNumber the destination account number
   * @return the created transaction
   */
  public Transaction execute(
      BigDecimal amount,
      String currency,
      String originAccountNumber,
      String destinationAccountNumber) {
    String transactionId = idGenerator.generateId();
    LocalDateTime now = LocalDateTime.now();

    Transaction transaction =
        Transaction.createPending(
            transactionId, amount, currency, originAccountNumber, destinationAccountNumber, now);

    return transactionRepository.save(transaction);
  }
}
