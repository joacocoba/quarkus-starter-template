package com.example.transactions.presentation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Request DTO for creating a new transaction.
 *
 * @param amount the transaction amount (must be positive)
 * @param currency the currency code (must be 3 characters)
 * @param originAccountNumber the origin account number (8-20 characters)
 * @param destinationAccountNumber the destination account number (8-20 characters)
 */
public record CreateTransactionRequest(
        @NotNull(message = "Amount is required") @DecimalMin(value = "0.01", message = "Amount must be positive")
                BigDecimal amount,
        @NotBlank(message = "Currency is required")
                @Size(min = 3, max = 3, message = "Currency must be exactly 3 characters")
                String currency,
        @NotBlank(message = "Origin account number is required")
                @Size(min = 8, max = 20, message = "Origin account number must be between 8 and 20 characters")
                String originAccountNumber,
        @NotBlank(message = "Destination account number is required")
                @Size(min = 8, max = 20, message = "Destination account number must be between 8 and 20 characters")
                String destinationAccountNumber) {}
