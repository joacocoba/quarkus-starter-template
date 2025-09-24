package com.example.transactions.presentation.dto;

import java.util.List;

/**
 * Response DTO for paginated transaction lists.
 *
 * @param transactions the list of transactions
 * @param total the total number of transactions available
 * @param offset the offset used for this page
 * @param limit the limit used for this page
 * @param hasNext whether there are more transactions available
 */
public record TransactionListResponse(
        List<TransactionResponse> transactions, long total, int offset, int limit, boolean hasNext) {

    /**
     * Creates a TransactionListResponse with pagination metadata.
     *
     * @param transactions the list of transaction responses
     * @param total the total count of transactions
     * @param offset the current offset
     * @param limit the current limit
     * @return the paginated response
     */
    public static TransactionListResponse of(
            List<TransactionResponse> transactions, long total, int offset, int limit) {
        boolean hasNext = (offset + limit) < total;
        return new TransactionListResponse(transactions, total, offset, limit, hasNext);
    }
}
