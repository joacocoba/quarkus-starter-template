package com.example.transactions.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TransactionTest {

  @Test
  void shouldCreatePendingTransaction() {
    // Given
    String id = "test-id";
    BigDecimal amount = new BigDecimal("100.00");
    String currency = "USD";
    String originAccount = "ACC-12345678";
    String destinationAccount = "ACC-87654321";
    LocalDateTime createdAt = LocalDateTime.now();

    // When
    Transaction transaction =
        Transaction.createPending(
            id, amount, currency, originAccount, destinationAccount, createdAt);

    // Then
    assertThat(transaction.getId()).isEqualTo(id);
    assertThat(transaction.getAmount()).isEqualTo(amount);
    assertThat(transaction.getCurrency()).isEqualTo(currency);
    assertThat(transaction.getOriginAccountNumber()).isEqualTo(originAccount);
    assertThat(transaction.getDestinationAccountNumber()).isEqualTo(destinationAccount);
    assertThat(transaction.getStatus()).isEqualTo(TransactionStatus.PENDING);
    assertThat(transaction.getCreatedAt()).isEqualTo(createdAt);
    assertThat(transaction.isFinal()).isFalse();
  }

  @Test
  void shouldCompleteTransaction() {
    // Given
    Transaction pending = createTestTransaction(TransactionStatus.PENDING);

    // When
    Transaction completed = pending.complete();

    // Then
    assertThat(completed.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
    assertThat(completed.isFinal()).isTrue();
    assertThat(completed.getId()).isEqualTo(pending.getId());
  }

  @Test
  void shouldFailTransaction() {
    // Given
    Transaction pending = createTestTransaction(TransactionStatus.PENDING);

    // When
    Transaction failed = pending.fail();

    // Then
    assertThat(failed.getStatus()).isEqualTo(TransactionStatus.FAILED);
    assertThat(failed.isFinal()).isTrue();
    assertThat(failed.getId()).isEqualTo(pending.getId());
  }

  @Test
  void shouldRejectNullId() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    null,
                    BigDecimal.TEN,
                    "USD",
                    "ACC-12345678",
                    "ACC-87654321",
                    LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Transaction ID cannot be null or empty");
  }

  @Test
  void shouldRejectEmptyId() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    "", BigDecimal.TEN, "USD", "ACC-12345678", "ACC-87654321", LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Transaction ID cannot be null or empty");
  }

  @Test
  void shouldRejectNullAmount() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    "id", null, "USD", "ACC-12345678", "ACC-87654321", LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Amount cannot be null");
  }

  @Test
  void shouldRejectNegativeAmount() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    "id",
                    new BigDecimal("-1"),
                    "USD",
                    "ACC-12345678",
                    "ACC-87654321",
                    LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Amount must be positive");
  }

  @Test
  void shouldRejectInvalidCurrency() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    "id",
                    BigDecimal.TEN,
                    "INVALID",
                    "ACC-12345678",
                    "ACC-87654321",
                    LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Currency must be a 3-character code");
  }

  @Test
  void shouldRejectInvalidOriginAccount() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    "id", BigDecimal.TEN, "USD", "SHORT", "ACC-87654321", LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Origin account number must be between 8 and 20 characters");
  }

  @Test
  void shouldRejectInvalidDestinationAccount() {
    // Given/When/Then
    assertThatThrownBy(
            () ->
                Transaction.createPending(
                    "id", BigDecimal.TEN, "USD", "ACC-12345678", null, LocalDateTime.now()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Destination account number cannot be null or empty");
  }

  @Test
  void shouldNormalizeCurrencyToUpperCase() {
    // Given/When
    Transaction transaction =
        Transaction.createPending(
            "id", BigDecimal.TEN, "usd", "ACC-12345678", "ACC-87654321", LocalDateTime.now());

    // Then
    assertThat(transaction.getCurrency()).isEqualTo("USD");
  }

  @Test
  void shouldImplementEqualsBasedOnId() {
    // Given
    LocalDateTime now = LocalDateTime.now();
    Transaction tx1 =
        Transaction.createPending(
            "same-id", BigDecimal.TEN, "USD", "ACC-12345678", "ACC-87654321", now);
    Transaction tx2 =
        Transaction.createPending(
            "same-id",
            new BigDecimal("20"),
            "EUR",
            "ACC-11111111",
            "ACC-22222222",
            now.plusMinutes(1));
    Transaction tx3 =
        Transaction.createPending(
            "different-id", BigDecimal.TEN, "USD", "ACC-12345678", "ACC-87654321", now);

    // Then
    assertThat(tx1).isEqualTo(tx2);
    assertThat(tx1).isNotEqualTo(tx3);
    assertThat(tx1.hashCode()).isEqualTo(tx2.hashCode());
  }

  private Transaction createTestTransaction(TransactionStatus status) {
    return new Transaction(
        "test-id",
        new BigDecimal("100.00"),
        "USD",
        "ACC-12345678",
        "ACC-87654321",
        status,
        LocalDateTime.now());
  }
}
