package com.example.transactions.application.transaction.mappers;

import com.example.transactions.application.transaction.dto.TransactionDto;
import com.example.transactions.domain.transaction.model.Transaction;

/**
 * Mapper for Transaction domain objects to/from DTOs.
 *
 * <p>This class handles the conversion between domain entities and data transfer objects for the
 * Transaction aggregate.
 */
public final class TransactionApplicationMapper {

  private TransactionApplicationMapper() {
    // Utility class
  }

  /**
   * Converts a Transaction domain entity to a DTO.
   *
   * @param transaction the domain entity
   * @return the DTO representation
   */
  public static TransactionDto toDto(Transaction transaction) {
    if (transaction == null) {
      return null;
    }

    return TransactionDto.builder()
        .id(transaction.getId())
        .amount(transaction.getAmount())
        .currency(transaction.getCurrency())
        .originAccountNumber(transaction.getOriginAccountNumber())
        .destinationAccountNumber(transaction.getDestinationAccountNumber())
        .status(transaction.getStatus().name())
        .createdAt(transaction.getCreatedAt())
        .build();
  }

  /**
   * Converts a collection of Transaction domain entities to DTOs.
   *
   * @param transactions the domain entities
   * @return the DTO representations
   */
  public static java.util.List<TransactionDto> toDtoList(java.util.List<Transaction> transactions) {
    if (transactions == null) {
      return java.util.Collections.emptyList();
    }

    return transactions.stream()
        .map(TransactionApplicationMapper::toDto)
        .collect(java.util.stream.Collectors.toList());
  }
}
