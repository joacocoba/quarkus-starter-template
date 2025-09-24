package com.example.transactions.application.dto;

/**
 * Query parameters for transaction listing operations.
 *
 * @param offset the number of records to skip
 * @param limit the maximum number of records to return
 */
public record TransactionQuery(int offset, int limit) {

  /**
   * Creates a TransactionQuery with validation.
   *
   * @param offset the number of records to skip (non-negative)
   * @param limit the maximum number of records to return (positive)
   */
  public TransactionQuery {
    if (offset < 0) {
      throw new IllegalArgumentException("Offset must be non-negative");
    }
    if (limit <= 0) {
      throw new IllegalArgumentException("Limit must be positive");
    }
  }

  /**
   * Creates a default query with offset 0 and limit 20.
   *
   * @return a default TransactionQuery
   */
  public static TransactionQuery defaultQuery() {
    return new TransactionQuery(0, 20);
  }

  /**
   * Creates a query with the specified limit and offset 0.
   *
   * @param limit the maximum number of records to return
   * @return a TransactionQuery with offset 0
   */
  public static TransactionQuery withLimit(int limit) {
    return new TransactionQuery(0, limit);
  }
}
