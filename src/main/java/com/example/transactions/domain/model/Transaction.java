package com.example.transactions.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Transaction aggregate root representing a financial transaction.
 *
 * <p>This is a pure domain entity with no framework dependencies, following Domain-Driven Design
 * principles.
 */
public class Transaction {

    private final String id;
    private final BigDecimal amount;
    private final String currency;
    private final String originAccountNumber;
    private final String destinationAccountNumber;
    private final TransactionStatus status;
    private final LocalDateTime createdAt;

    /**
     * Creates a new Transaction.
     *
     * @param id the unique transaction identifier
     * @param amount the transaction amount (must be positive)
     * @param currency the currency code (e.g., "USD", "EUR")
     * @param originAccountNumber the origin account number
     * @param destinationAccountNumber the destination account number
     * @param status the current transaction status
     * @param createdAt the transaction creation timestamp
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Transaction(
            String id, 
            BigDecimal amount, 
            String currency, 
            String originAccountNumber,
            String destinationAccountNumber,
            TransactionStatus status, 
            LocalDateTime createdAt) {
        this.id = validateId(id);
        this.amount = validateAmount(amount);
        this.currency = validateCurrency(currency);
        this.originAccountNumber = validateAccountNumber(originAccountNumber, "Origin account number");
        this.destinationAccountNumber = validateAccountNumber(destinationAccountNumber, "Destination account number");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt cannot be null");
    }

    /**
     * Factory method to create a new pending transaction.
     *
     * @param id the transaction ID
     * @param amount the transaction amount
     * @param currency the currency code
     * @param originAccountNumber the origin account number
     * @param destinationAccountNumber the destination account number
     * @param createdAt the creation timestamp
     * @return a new pending transaction
     */
    public static Transaction createPending(
            String id, 
            BigDecimal amount, 
            String currency, 
            String originAccountNumber,
            String destinationAccountNumber,
            LocalDateTime createdAt) {
        return new Transaction(id, amount, currency, originAccountNumber, destinationAccountNumber, TransactionStatus.PENDING, createdAt);
    }

    /**
     * Creates a completed version of this transaction.
     *
     * @return a new transaction instance with COMPLETED status
     */
    public Transaction complete() {
        if (status == TransactionStatus.COMPLETED) {
            return this;
        }
        return new Transaction(id, amount, currency, originAccountNumber, destinationAccountNumber, TransactionStatus.COMPLETED, createdAt);
    }

    /**
     * Creates a failed version of this transaction.
     *
     * @return a new transaction instance with FAILED status
     */
    public Transaction fail() {
        if (status == TransactionStatus.FAILED) {
            return this;
        }
        return new Transaction(id, amount, currency, originAccountNumber, destinationAccountNumber, TransactionStatus.FAILED, createdAt);
    }

    /**
     * Checks if the transaction is in a final state (completed or failed).
     *
     * @return true if the transaction is completed or failed
     */
    public boolean isFinal() {
        return status == TransactionStatus.COMPLETED || status == TransactionStatus.FAILED;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    private String validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or empty");
        }
        return id.trim();
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return amount;
    }

    private String validateCurrency(String currency) {
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        String trimmedCurrency = currency.trim().toUpperCase();
        if (trimmedCurrency.length() != 3) {
            throw new IllegalArgumentException("Currency must be a 3-character code");
        }
        return trimmedCurrency;
    }

    private String validateAccountNumber(String accountNumber, String fieldName) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        String trimmedAccount = accountNumber.trim();
        if (trimmedAccount.length() < 8 || trimmedAccount.length() > 20) {
            throw new IllegalArgumentException(fieldName + " must be between 8 and 20 characters");
        }
        // Basic validation: only alphanumeric characters and hyphens
        if (!trimmedAccount.matches("^[A-Za-z0-9-]+$")) {
            throw new IllegalArgumentException(fieldName + " can only contain letters, numbers, and hyphens");
        }
        return trimmedAccount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction that = (Transaction) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{"
                + "id='"
                + id
                + '\''
                + ", amount="
                + amount
                + ", currency='"
                + currency
                + '\''
                + ", originAccountNumber='"
                + originAccountNumber
                + '\''
                + ", destinationAccountNumber='"
                + destinationAccountNumber
                + '\''
                + ", status="
                + status
                + ", createdAt="
                + createdAt
                + '}';
    }
}
