package com.example.transactions.infrastructure.services;

import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;

import com.example.transactions.domain.shared.ports.IdGeneratorPort;

/**
 * UUID-based implementation of IdGeneratorPort.
 *
 * <p>This adapter generates random UUIDs as transaction identifiers, providing globally unique IDs
 * suitable for distributed systems.
 */
@ApplicationScoped
public class UuidGeneratorAdapter implements IdGeneratorPort {

  @Override
  public String generateId() {
    return UUID.randomUUID().toString();
  }
}
