package com.example.transactions.config;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import com.example.transactions.application.shared.exceptions.ValidationException;
import com.example.transactions.domain.shared.exceptions.DomainException;
import com.example.transactions.domain.transaction.exceptions.TransactionNotFoundException;

/**
 * Manejador global de excepciones para el API REST del servicio de transacciones.
 *
 * <p>Este componente centraliza el manejo de todas las excepciones que pueden ocurrir durante el
 * procesamiento de las solicitudes REST, proporcionando respuestas HTTP consistentes y bien
 * estructuradas.
 *
 * @author Sistema de Transacciones BPD
 * @version 1.0
 * @since 2024-09-24
 */
@Provider
@ApplicationScoped
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

  private static final Logger logger = Logger.getLogger(GlobalExceptionMapper.class.getName());

  // Constructor para debug
  public GlobalExceptionMapper() {
    logger.info("***** GlobalExceptionMapper configurado y registrado *****");
    System.out.println("***** GlobalExceptionMapper configurado y registrado *****");
  }

  @Override
  public Response toResponse(Throwable exception) {
    logger.info("GlobalExceptionMapper procesando: " + exception.getClass().getSimpleName());
    System.out.println("GlobalExceptionMapper procesando: " + exception.getClass().getSimpleName());

    // Log de la excepción
    logger.severe(
        "Excepción capturada: "
            + exception.getClass().getSimpleName()
            + " - "
            + exception.getMessage());

    // Manejar TransactionNotFoundException específicamente
    if (exception instanceof TransactionNotFoundException) {
      return handleTransactionNotFound((TransactionNotFoundException) exception);
    }

    // Manejar ValidationException
    if (exception instanceof ValidationException) {
      return handleValidationError((ValidationException) exception);
    }

    // Manejar DomainException
    if (exception instanceof DomainException) {
      return handleDomainError((DomainException) exception);
    }

    // Manejar IllegalArgumentException
    if (exception instanceof IllegalArgumentException) {
      return handleBadRequest((IllegalArgumentException) exception);
    }

    // Cualquier otra excepción
    return handleGenericError(exception);
  }

  private Response handleTransactionNotFound(TransactionNotFoundException ex) {
    logger.info("Manejando TransactionNotFoundException: " + ex.getMessage());

    Map<String, Object> errorResponse =
        Map.of(
            "error",
            "Transacción no encontrada",
            "message",
            ex.getMessage(),
            "code",
            "TRANSACTION_NOT_FOUND",
            "timestamp",
            LocalDateTime.now().toString(),
            "status",
            404);

    return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
  }

  private Response handleValidationError(ValidationException ex) {
    Map<String, Object> errorResponse =
        Map.of(
            "error",
            "Error de validación",
            "message",
            ex.getMessage(),
            "code",
            "VALIDATION_ERROR",
            "timestamp",
            LocalDateTime.now().toString(),
            "status",
            400);

    return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
  }

  private Response handleDomainError(DomainException ex) {
    Map<String, Object> errorResponse =
        Map.of(
            "error",
            "Error del dominio",
            "message",
            ex.getMessage(),
            "code",
            "DOMAIN_ERROR",
            "timestamp",
            LocalDateTime.now().toString(),
            "status",
            400);

    return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
  }

  private Response handleBadRequest(IllegalArgumentException ex) {
    Map<String, Object> errorResponse =
        Map.of(
            "error",
            "Solicitud inválida",
            "message",
            ex.getMessage(),
            "code",
            "BAD_REQUEST",
            "timestamp",
            LocalDateTime.now().toString(),
            "status",
            400);

    return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
  }

  private Response handleGenericError(Throwable ex) {
    Map<String, Object> errorResponse =
        Map.of(
            "error",
            "Error interno del servidor",
            "message",
            ex.getMessage() != null ? ex.getMessage() : "Error desconocido",
            "code",
            "INTERNAL_SERVER_ERROR",
            "timestamp",
            LocalDateTime.now().toString(),
            "status",
            500);

    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
  }
}
