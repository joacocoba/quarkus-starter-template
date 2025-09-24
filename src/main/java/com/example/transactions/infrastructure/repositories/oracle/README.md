# Oracle Database Integration Guide

This directory contains the Oracle database adapter implementation for the transactions service.

## Current Status

🚧 **Work in Progress** - The Oracle adapter is currently a placeholder with documentation and TODO comments.

## Implementation Steps

To complete the Oracle integration:

### 1. Dependencies

Uncomment the following dependencies in `pom.xml`:

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-orm</artifactId>
</dependency>
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jdbc-oracle</artifactId>
</dependency>
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc11</artifactId>
    <version>23.5.0.24.07</version>
</dependency>
```

### 2. Database Schema

Create migration scripts in `src/main/resources/db/migration/`:

- `V1__create_transactions_table.sql`
- `V2__add_indexes.sql` (if needed)

Example V1 migration:

```sql
CREATE TABLE transactions (
    id VARCHAR2(36) PRIMARY KEY,
    amount NUMBER(15,2) NOT NULL,
    currency VARCHAR2(3) NOT NULL,
    status VARCHAR2(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_transactions_created_at ON transactions(created_at);
CREATE INDEX idx_transactions_status ON transactions(status);
```

### 3. JPA Entity

Either:
- Add JPA annotations to the domain `Transaction` class, or
- Create a separate `TransactionEntity` with mapping utilities

### 4. Configuration

Update `application-prod.properties`:

```properties
# Database Configuration
app.repository.type=oracle
quarkus.datasource.db-kind=oracle
quarkus.datasource.username=${DB_USERNAME:transactions_user}
quarkus.datasource.password=${DB_PASSWORD}
quarkus.datasource.jdbc.url=${DB_URL:jdbc:oracle:thin:@localhost:1521:XE}
quarkus.datasource.jdbc.max-size=20
quarkus.datasource.jdbc.min-size=5

# Hibernate ORM
quarkus.hibernate-orm.database.generation=validate
quarkus.hibernate-orm.sql-load-script=no-file

# Flyway Migration
quarkus.flyway.migrate-at-start=true
quarkus.flyway.locations=classpath:db/migration
```

### 5. Implementation

Complete the methods in `OracleTransactionRepositoryAdapter.java` using either:

- **EntityManager** (JPA approach)
- **Panache Repository** pattern (Quarkus-native approach)

### 6. Testing

Add integration tests for the Oracle adapter:

```java
@QuarkusTest
@TestProfile(OracleTestProfile.class)
public class OracleTransactionRepositoryAdapterTest {
    // Test implementation
}
```

## Environment Variables

Set these environment variables in production:

- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password  
- `DB_URL`: JDBC connection URL

## Connection Pool Configuration

The Oracle adapter includes connection pooling configuration:

- **Min Pool Size**: 5 connections
- **Max Pool Size**: 20 connections
- **Idle Timeout**: Managed by Agroal (default 5 minutes)

## Performance Considerations

1. **Indexes**: Ensure proper indexes on frequently queried columns
2. **Connection Pooling**: Tune pool sizes based on load
3. **Query Optimization**: Use EXPLAIN PLAN for complex queries
4. **Batch Operations**: Consider batch inserts for high-volume scenarios

## Monitoring

Enable database metrics and health checks:

```properties
quarkus.micrometer.binder.sql.enabled=true
quarkus.smallrye-health.additional-property.oracle.status=UP
```
