package com.example.transactions.presentation.rest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.example.transactions.shared.errors.DomainException;
import com.example.transactions.shared.errors.TransactionNotFoundException;
import com.example.transactions.shared.errors.ValidationException;

/**
 * Global exception mapper for consistent error handling across the API.
 *
 * <p>This mapper converts domain exceptions and validation errors into proper HTTP responses with
 * structured error information.
 */
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

  private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

  @Override
  public Response toResponse(Exception exception) {
    LOG.error("Exception occurred", exception);

    return switch (exception) {
      case TransactionNotFoundException ex -> handleTransactionNotFound(ex);
      case ValidationException ex -> handleValidationException(ex);
      case ConstraintViolationException ex -> handleConstraintViolation(ex);
      case DomainException ex -> handleDomainException(ex);
      case IllegalArgumentException ex -> handleIllegalArgument(ex);
      default -> handleGenericException(exception);
    };
  }

  private Response handleTransactionNotFound(TransactionNotFoundException ex) {
    ErrorResponse error =
        new ErrorResponse(
            ex.getErrorCode(),
            ex.getMessage(),
            "The requested transaction could not be found",
            LocalDateTime.now(),
            null);

    return Response.status(Response.Status.NOT_FOUND)
        .type(MediaType.APPLICATION_JSON)
        .entity(error)
        .build();
  }

  private Response handleValidationException(ValidationException ex) {
    ErrorResponse error =
        new ErrorResponse(
            ex.getErrorCode(), ex.getMessage(), "Validation failed", LocalDateTime.now(), null);

    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(error)
        .build();
  }

  private Response handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> violations =
        ex.getConstraintViolations().stream()
            .collect(
                Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    ConstraintViolation::getMessage));

    ErrorResponse error =
        new ErrorResponse(
            "VALIDATION_ERROR",
            "Request validation failed",
            "One or more fields contain invalid values",
            LocalDateTime.now(),
            violations);

    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(error)
        .build();
  }

  private Response handleDomainException(DomainException ex) {
    ErrorResponse error =
        new ErrorResponse(
            ex.getErrorCode(), ex.getMessage(), "Domain rule violation", LocalDateTime.now(), null);

    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(error)
        .build();
  }

  private Response handleIllegalArgument(IllegalArgumentException ex) {
    ErrorResponse error =
        new ErrorResponse(
            "INVALID_ARGUMENT",
            ex.getMessage(),
            "Invalid request parameter",
            LocalDateTime.now(),
            null);

    return Response.status(Response.Status.BAD_REQUEST)
        .type(MediaType.APPLICATION_JSON)
        .entity(error)
        .build();
  }

  private Response handleGenericException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse(
            "INTERNAL_ERROR",
            "An unexpected error occurred",
            "Please try again later or contact support if the problem persists",
            LocalDateTime.now(),
            null);

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .type(MediaType.APPLICATION_JSON)
        .entity(error)
        .build();
  }

  /** Standard error response structure. */
  public record ErrorResponse(
      String code,
      String message,
      String detail,
      LocalDateTime timestamp,
      Map<String, String> violations) {}
}
