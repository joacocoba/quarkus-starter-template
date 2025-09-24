package com.example.transactions.shared.constants;

/**
 * Constants for API configuration and paths.
 * 
 * <p>This class centralizes API-related constants to avoid repetition and ensure consistency
 * across the application.
 */
public final class ApiConstants {

    /**
     * API version prefix used in all REST endpoints.
     */
    public static final String API_VERSION_V1 = "/api/v1";

    /**
     * Base path for transaction endpoints.
     */
    public static final String TRANSACTIONS_PATH = API_VERSION_V1 + "/transactions";

    /**
     * Private constructor to prevent instantiation.
     */
    private ApiConstants() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
