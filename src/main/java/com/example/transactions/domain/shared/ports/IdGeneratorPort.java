package com.example.transactions.domain.shared.ports;

/**
 * Port (interface) for generating unique identifiers.
 *
 * <p>This interface abstracts the ID generation strategy, allowing different implementations (UUID,
 * sequential, etc.) to be plugged in via the infrastructure layer.
 */
public interface IdGeneratorPort {

  /**
   * Generates a new unique identifier.
   *
   * @return a unique ID string
   */
  String generateId();
}
