package com.example.transactions.presentation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.transactions.domain.model.Transaction;
import com.example.transactions.domain.model.TransactionStatus;

/**
 * Response DTO for transaction data.
 *
 * @param id the transaction ID
 * @param amount the transaction amount
 * @param currency the currency code
 * @param originAccountNumber the origin account number
 * @param destinationAccountNumber the destination account number
 * @param status the transaction status
 * @param createdAt the creation timestamp
 */
public record TransactionResponse(
    String id,
    BigDecimal amount,
    String currency,
    String originAccountNumber,
    String destinationAccountNumber,
    TransactionStatus status,
    LocalDateTime createdAt) {

  /**
   * Creates a TransactionResponse from a domain Transaction.
   *
   * @param transaction the domain transaction
   * @return the response DTO
   */
  public static TransactionResponse fromDomain(Transaction transaction) {
    return new TransactionResponse(
        transaction.getId(),
        transaction.getAmount(),
        transaction.getCurrency(),
        transaction.getOriginAccountNumber(),
        transaction.getDestinationAccountNumber(),
        transaction.getStatus(),
        transaction.getCreatedAt());
  }
}
