package com.example.transactions.application.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Transaction information.
 *
 * <p>This record represents transaction data in the application layer, used for transferring data
 * between use cases and presentation layer.
 */
public record TransactionDto(
    String id,
    BigDecimal amount,
    String currency,
    String originAccountNumber,
    String destinationAccountNumber,
    String status,
    LocalDateTime createdAt) {

  /**
   * Creates a builder for TransactionDto.
   *
   * @return a new builder instance
   */
  public static Builder builder() {
    return new Builder();
  }

  /** Builder class for TransactionDto. */
  public static class Builder {
    private String id;
    private BigDecimal amount;
    private String currency;
    private String originAccountNumber;
    private String destinationAccountNumber;
    private String status;
    private LocalDateTime createdAt;

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder amount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder originAccountNumber(String originAccountNumber) {
      this.originAccountNumber = originAccountNumber;
      return this;
    }

    public Builder destinationAccountNumber(String destinationAccountNumber) {
      this.destinationAccountNumber = destinationAccountNumber;
      return this;
    }

    public Builder status(String status) {
      this.status = status;
      return this;
    }

    public Builder createdAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public TransactionDto build() {
      return new TransactionDto(
          id, amount, currency, originAccountNumber, destinationAccountNumber, status, createdAt);
    }
  }
}
