package com.example.transactions.application.transaction.policies;

import java.math.BigDecimal;

import jakarta.enterprise.context.ApplicationScoped;

import com.example.transactions.application.shared.exceptions.ValidationException;

/**
 * Validation policies for transaction operations.
 *
 * <p>This class centralizes business validation rules specific to transactions, ensuring
 * consistency across all transaction-related use cases.
 */
@ApplicationScoped
public class TransactionValidationPolicy {

  private static final BigDecimal MIN_AMOUNT = new BigDecimal("0.01");
  private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000.00");

  /**
   * Validates transaction amount constraints.
   *
   * @param amount the transaction amount
   * @throws ValidationException if amount is invalid
   */
  public void validateAmount(BigDecimal amount) {
    if (amount == null) {
      throw new ValidationException("Transaction amount cannot be null");
    }

    if (amount.compareTo(MIN_AMOUNT) < 0) {
      throw new ValidationException("Transaction amount must be at least " + MIN_AMOUNT);
    }

    if (amount.compareTo(MAX_AMOUNT) > 0) {
      throw new ValidationException("Transaction amount cannot exceed " + MAX_AMOUNT);
    }
  }

  /**
   * Validates currency code format.
   *
   * @param currency the currency code
   * @throws ValidationException if currency is invalid
   */
  public void validateCurrency(String currency) {
    if (currency == null || currency.trim().isEmpty()) {
      throw new ValidationException("Currency code cannot be empty");
    }

    if (currency.length() != 3) {
      throw new ValidationException("Currency code must be exactly 3 characters");
    }

    if (!currency.matches("[A-Z]{3}")) {
      throw new ValidationException("Currency code must contain only uppercase letters");
    }
  }

  /**
   * Validates account number format.
   *
   * @param accountNumber the account number
   * @throws ValidationException if account number is invalid
   */
  public void validateAccountNumber(String accountNumber) {
    if (accountNumber == null || accountNumber.trim().isEmpty()) {
      throw new ValidationException("Account number cannot be empty");
    }

    if (accountNumber.length() < 8 || accountNumber.length() > 20) {
      throw new ValidationException("Account number must be between 8 and 20 characters");
    }

    if (!accountNumber.matches("\\d+")) {
      throw new ValidationException("Account number must contain only digits");
    }
  }

  /**
   * Validates that origin and destination accounts are different.
   *
   * @param originAccount the origin account number
   * @param destinationAccount the destination account number
   * @throws ValidationException if accounts are the same
   */
  public void validateDifferentAccounts(String originAccount, String destinationAccount) {
    if (originAccount != null && originAccount.equals(destinationAccount)) {
      throw new ValidationException("Origin and destination accounts must be different");
    }
  }
}
