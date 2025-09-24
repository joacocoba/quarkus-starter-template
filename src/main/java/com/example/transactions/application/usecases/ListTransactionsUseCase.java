package com.example.transactions.application.usecases;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import com.example.transactions.application.dto.TransactionQuery;
import com.example.transactions.domain.model.Transaction;
import com.example.transactions.domain.ports.TransactionRepositoryPort;

/**
 * Use case for listing transactions with pagination support.
 *
 * <p>This class handles the retrieval of multiple transactions based on query parameters.
 */
@ApplicationScoped
public class ListTransactionsUseCase {

  private final TransactionRepositoryPort transactionRepository;

  @Inject
  public ListTransactionsUseCase(TransactionRepositoryPort transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  /**
   * Lists transactions based on the provided query parameters.
   *
   * @param query the query parameters including pagination
   * @return a list of transactions
   */
  public List<Transaction> execute(TransactionQuery query) {
    int validatedOffset = Math.max(0, query.offset());
    int validatedLimit = Math.min(Math.max(1, query.limit()), 100); // Max 100 items per page

    return transactionRepository.findAll(validatedOffset, validatedLimit);
  }

  /**
   * Gets the total count of transactions.
   *
   * @return the total count
   */
  public long getTotalCount() {
    return transactionRepository.count();
  }
}
