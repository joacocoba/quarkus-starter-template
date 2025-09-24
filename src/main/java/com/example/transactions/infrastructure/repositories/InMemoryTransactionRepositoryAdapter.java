package com.example.transactions.infrastructure.repositories;

import com.example.transactions.domain.model.Transaction;
import com.example.transactions.domain.ports.TransactionRepositoryPort;
import io.quarkus.arc.lookup.LookupIfProperty;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of TransactionRepositoryPort for development and testing.
 *
 * <p>This adapter provides a simple in-memory storage mechanism using a ConcurrentHashMap. It is
 * activated when the profile is set to 'dev' or 'test', or when no database configuration is
 * provided.
 */
@ApplicationScoped
@LookupIfProperty(name = "app.repository.type", stringValue = "in-memory", lookupIfMissing = true)
public class InMemoryTransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    @Override
    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactions.get(id));
    }

    @Override
    public List<Transaction> findAll(int offset, int limit) {
        List<Transaction> allTransactions = new ArrayList<>(transactions.values());

        // Sort by creation date (newest first) for consistent ordering
        allTransactions.sort((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()));

        int start = Math.min(offset, allTransactions.size());
        int end = Math.min(offset + limit, allTransactions.size());

        return allTransactions.subList(start, end);
    }

    @Override
    public long count() {
        return transactions.size();
    }

    @Override
    public boolean existsById(String id) {
        return transactions.containsKey(id);
    }

    /**
     * Clears all transactions. Useful for testing.
     */
    public void clear() {
        transactions.clear();
    }

    /**
     * Gets the current size of the in-memory store.
     *
     * @return the number of stored transactions
     */
    public int size() {
        return transactions.size();
    }
}
