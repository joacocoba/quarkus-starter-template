package com.example.transactions.shared.errors;

/**
 * Base class for all domain-specific exceptions.
 *
 * <p>This exception serves as the parent for all business logic exceptions in the transactions
 * domain. It provides a consistent error handling mechanism across the application.
 */
public abstract class DomainException extends RuntimeException {

    private final String errorCode;

    protected DomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    protected DomainException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Gets the error code associated with this exception.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }
}
