package com.example.transactions.infrastructure.repositories.oracle;

import java.util.List;
import java.util.Optional;

import com.example.transactions.domain.model.Transaction;
import com.example.transactions.domain.ports.TransactionRepositoryPort;

/**
 * Oracle database implementation of TransactionRepositoryPort.
 *
 * <p>This adapter provides Oracle database persistence for transactions using Hibernate ORM and
 * JDBC. It is activated when the application profile is set to 'prod' and database configuration is
 * provided.
 *
 * <p><strong>TODO:</strong> This is a placeholder implementation. To complete:
 *
 * <ol>
 *   <li>Uncomment Oracle dependencies in pom.xml
 *   <li>Add JPA entity mapping annotations to Transaction class or create a separate entity
 *   <li>Implement the actual database operations using EntityManager or Panache
 *   <li>Add database migration scripts in src/main/resources/db/migration/
 *   <li>Configure datasource properties in application-prod.properties
 * </ol>
 *
 * <p>Example configuration keys needed in application-prod.properties:
 *
 * <pre>
 * quarkus.datasource.db-kind=oracle
 * quarkus.datasource.username=${DB_USERNAME:transactions_user}
 * quarkus.datasource.password=${DB_PASSWORD}
 * quarkus.datasource.jdbc.url=${DB_URL:jdbc:oracle:thin:@localhost:1521:XE}
 * quarkus.datasource.jdbc.max-size=20
 * quarkus.datasource.jdbc.min-size=5
 *
 * quarkus.hibernate-orm.database.generation=validate
 * quarkus.hibernate-orm.sql-load-script=no-file
 * quarkus.flyway.migrate-at-start=true
 * quarkus.flyway.locations=classpath:db/migration
 * </pre>
 */
// TODO: Temporarily disabled to avoid CDI conflicts - enable when Oracle integration is ready
// @ApplicationScoped
// @LookupIfProperty(name = "app.repository.type", stringValue = "oracle")
public class OracleTransactionRepositoryAdapter implements TransactionRepositoryPort {

  // TODO: Inject EntityManager or use Panache Repository pattern
  // @Inject
  // EntityManager entityManager;

  @Override
  public Transaction save(Transaction transaction) {
    // TODO: Implement Oracle persistence
    // Example with EntityManager:
    // TransactionEntity entity = TransactionMapper.toEntity(transaction);
    // entityManager.persist(entity);
    // return TransactionMapper.toDomain(entity);
    throw new UnsupportedOperationException("Oracle repository not yet implemented");
  }

  @Override
  public Optional<Transaction> findById(String id) {
    // TODO: Implement Oracle query
    // Example with EntityManager:
    // TransactionEntity entity = entityManager.find(TransactionEntity.class, id);
    // return entity != null ? Optional.of(TransactionMapper.toDomain(entity)) :
    // Optional.empty();
    throw new UnsupportedOperationException("Oracle repository not yet implemented");
  }

  @Override
  public List<Transaction> findAll(int offset, int limit) {
    // TODO: Implement Oracle query with pagination
    // Example with EntityManager:
    // TypedQuery<TransactionEntity> query = entityManager
    //     .createQuery("SELECT t FROM TransactionEntity t ORDER BY t.createdAt DESC",
    // TransactionEntity.class)
    //     .setFirstResult(offset)
    //     .setMaxResults(limit);
    // return query.getResultStream()
    //     .map(TransactionMapper::toDomain)
    //     .toList();
    throw new UnsupportedOperationException("Oracle repository not yet implemented");
  }

  @Override
  public long count() {
    // TODO: Implement Oracle count query
    // Example with EntityManager:
    // return entityManager.createQuery("SELECT COUNT(t) FROM TransactionEntity t",
    // Long.class)
    //     .getSingleResult();
    throw new UnsupportedOperationException("Oracle repository not yet implemented");
  }

  @Override
  public boolean existsById(String id) {
    // TODO: Implement Oracle exists query
    // Example with EntityManager:
    // Long count = entityManager
    //     .createQuery("SELECT COUNT(t) FROM TransactionEntity t WHERE t.id = :id",
    // Long.class)
    //     .setParameter("id", id)
    //     .getSingleResult();
    // return count > 0;
    throw new UnsupportedOperationException("Oracle repository not yet implemented");
  }
}
