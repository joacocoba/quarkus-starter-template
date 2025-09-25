package com.example.transactions.application.transaction.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Command for creating a new transaction.
 *
 * <p>This record encapsulates the input data required to create a new transaction, including
 * validation constraints.
 */
public record CreateTransactionCommand(
    @NotNull(message = "Amount cannot be null") @Positive(message = "Amount must be positive") BigDecimal amount,
    @NotBlank(message = "Currency cannot be blank") String currency,
    @NotBlank(message = "Origin account number cannot be blank") String originAccountNumber,
    @NotBlank(message = "Destination account number cannot be blank") String destinationAccountNumber) {

  /** Creates a CreateTransactionCommand with validation. */
  public CreateTransactionCommand {
    if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }
    if (currency != null && currency.trim().isEmpty()) {
      throw new IllegalArgumentException("Currency cannot be blank");
    }
    if (originAccountNumber != null && originAccountNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Origin account number cannot be blank");
    }
    if (destinationAccountNumber != null && destinationAccountNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Destination account number cannot be blank");
    }
  }
}
