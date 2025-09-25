package com.example.transactions.application.shared.exceptions;

import com.example.transactions.domain.shared.exceptions.DomainException;

/**
 * Exception thrown when validation fails.
 *
 * <p>This exception is thrown when input validation fails at the application boundary.
 */
public class ValidationException extends DomainException {

  public ValidationException(String message) {
    super("VALIDATION_ERROR", message);
  }

  public ValidationException(String message, Throwable cause) {
    super("VALIDATION_ERROR", message, cause);
  }
}
