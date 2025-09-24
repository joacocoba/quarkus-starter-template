package com.example.transactions.infrastructure.services;

import com.example.transactions.domain.ports.IdGeneratorPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

/**
 * UUID-based implementation of IdGeneratorPort.
 *
 * <p>This adapter generates random UUIDs as transaction identifiers, providing globally unique
 * IDs suitable for distributed systems.
 */
@ApplicationScoped
public class UuidGeneratorAdapter implements IdGeneratorPort {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
