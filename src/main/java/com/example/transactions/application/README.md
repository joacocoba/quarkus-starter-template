# ⚙️ Application Layer - Casos de Uso por Entidades

Esta capa contiene la **lógica de aplicación** organizada por entidades de negocio, implementando casos de uso específicos y coordinando entre el dominio y la infraestructura.

## 📁 Estructura por Entidades

```
application/
├── transaction/              # Casos de uso de Transaction
│   ├── dto/                 # Data Transfer Objects
│   ├── usecases/            # Use Cases específicos
│   ├── mappers/             # Mappers entre capas
│   └── policies/            # Políticas de validación
└── shared/                  # Componentes compartidos
    ├── pagination/          # Paginación común
    ├── validation/          # Validaciones compartidas
    └── exceptions/          # Excepciones de aplicación
```

## 🎯 Transaction Application

### 📄 `transaction/dto/`
**Data Transfer Objects para casos de uso**

- **`TransactionQuery.java`** - Query parameters para listado
  - Campos: `offset`, `limit`
  - Validación de paginación
  - Inmutable record class

- **`TransactionDto.java`** - DTO de respuesta de aplicación
  - Representation de Transaction para application layer
  - Diferentes de presentation DTOs
  - Clean separation of concerns

- **`CreateTransactionCommand.java`** - Command para creación
  - Command pattern para write operations
  - Validation rules
  - Immutable command object

**Principios**:
- **Command/Query Separation**: DTOs específicos para lectura vs escritura
- **Layer Isolation**: DTOs específicos por capa (app vs presentation)
- **Validation**: Business rules validation en DTOs

### 🎭 `transaction/usecases/`
**Casos de uso del negocio Transaction**

#### **`CreateTransactionUseCase.java`**
```java
@ApplicationScoped
public class CreateTransactionUseCase {
    
    public Transaction execute(BigDecimal amount, String currency, 
                              String origin, String destination) {
        // 1. Generate ID
        String id = idGenerator.generateId();
        
        // 2. Create domain entity
        Transaction transaction = Transaction.createPending(/*...*/);
        
        // 3. Persist
        return transactionRepository.save(transaction);
    }
}
```

#### **`GetTransactionUseCase.java`**
```java
@ApplicationScoped  
public class GetTransactionUseCase {
    
    public Transaction execute(String transactionId) {
        return transactionRepository.findById(transactionId)
            .orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }
}
```

#### **`ListTransactionsUseCase.java`**  
```java
@ApplicationScoped
public class ListTransactionsUseCase {
    
    public List<Transaction> execute(TransactionQuery query) {
        int validatedOffset = Math.max(0, query.offset());
        int validatedLimit = Math.min(Math.max(1, query.limit()), 100);
        
        return transactionRepository.findAll(validatedOffset, validatedLimit);
    }
    
    public long getTotalCount() {
        return transactionRepository.count();
    }
}
```

**Principios de Use Cases**:
- **Single Responsibility**: Un use case, una responsabilidad de negocio
- **Orchestration**: Coordina domain objects e infrastructure
- **Stateless**: No mantienen estado entre llamadas
- **Transaction Boundaries**: Define boundaries transaccionales

### 🗺️ `transaction/mappers/`
**Mappers para transformación entre capas**

- **`TransactionApplicationMapper.java`** - Mapeo aplicación ↔ dominio
  - `Transaction` → `TransactionDto`
  - Mapping logic centralizado
  - No mapping libraries (MapStruct optional)

```java
public class TransactionApplicationMapper {
    
    public static TransactionDto toDto(Transaction transaction) {
        return new TransactionDto(
            transaction.getId(),
            transaction.getAmount(),
            // ... mapping logic
        );
    }
    
    // No reverse mapping needed (domain ← application)
    // Domain should not depend on application DTOs
}
```

**Principios de Mapping**:
- **One-Way Dependency**: Application → Domain, no reverse
- **Explicit Mapping**: No magic, clear transformation rules
- **Centralized**: All mapping logic in dedicated mappers

### 🛡️ `transaction/policies/`
**Políticas de validación de negocio**

- **`TransactionValidationPolicy.java`** - Reglas de validación
  - Business rules validation
  - Cross-field validation
  - Amount limits, currency validation, etc.

```java
@ApplicationScoped
public class TransactionValidationPolicy {
    
    public void validateCreateTransaction(BigDecimal amount, String currency,
                                        String origin, String destination) {
        validateAmount(amount);
        validateCurrency(currency);
        validateAccounts(origin, destination);
        validateTransactionLimits(amount);
    }
    
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Amount must be positive");
        }
        if (amount.compareTo(MAX_TRANSACTION_AMOUNT) > 0) {
            throw new ValidationException("Amount exceeds maximum limit");
        }
    }
}
```

**Principios de Policies**:
- **Business Rules**: Encapsula reglas de negocio complejas
- **Reusable**: Policies reutilizables entre use cases
- **Explicit**: Validation rules explicitas y claras
- **Domain Independent**: Policies específicas de application layer

## 🔄 Shared Application

### 📊 `shared/pagination/`
**Componentes de paginación reutilizables**

- **`PageRequest.java`** - Request paginado estándar
- **`PageResponse.java`** - Response paginado estándar

```java
public record PageRequest(
    @Min(0) int offset,
    @Min(1) @Max(100) int limit
) {
    public PageRequest {
        if (offset < 0) offset = 0;
        if (limit < 1) limit = 20;
        if (limit > 100) limit = 100;
    }
}

public record PageResponse<T>(
    List<T> content,
    long totalElements,
    int offset,
    int limit,
    boolean hasNext
) {
    public static <T> PageResponse<T> of(List<T> content, long total, int offset, int limit) {
        boolean hasNext = (offset + limit) < total;
        return new PageResponse<>(content, total, offset, limit, hasNext);
    }
}
```

### ✅ `shared/validation/`
**Utilidades de validación compartidas**

```java
// Futuro: Validation utilities
public class ValidationUtils {
    public static void validateAccountNumber(String accountNumber) {
        // Shared validation logic
    }
    
    public static void validateCurrency(String currency) {
        // Currency validation logic
    }
}
```

### 🚨 `shared/exceptions/`
**Excepciones específicas de la capa de aplicación**

- **`ValidationException.java`** - Errores de validación de input
  - Hereda de `DomainException`
  - Error code: `VALIDATION_ERROR`
  - Para validation failures en application layer

## 🎯 Flujo de Casos de Uso

### Transaction Creation Flow
```
[REST Request] 
    ↓
[TransactionResource] converts to use case parameters
    ↓
[CreateTransactionUseCase.execute()] 
    ↓ 
[TransactionValidationPolicy.validate()] validates business rules
    ↓
[Transaction.createPending()] creates domain entity
    ↓
[TransactionRepositoryPort.save()] persists via infrastructure
    ↓
[TransactionApplicationMapper.toDto()] converts for response
    ↓
[TransactionResponse] returned to client
```

**Flow Principles**:
- **Clear Separation**: Each layer has specific responsibility
- **Business First**: Validation and business logic early in flow
- **Domain Centric**: Domain entities control business behavior
- **Technology Agnostic**: Use cases don't know about REST, DB, etc.

## 🚀 Escalabilidad

### Nuevas Entidades
Para agregar `Account` entity:

```
application/
├── transaction/             # ✅ Existente
└── account/                 # 🆕 Nueva entidad
    ├── dto/
    │   ├── AccountQuery.java
    │   ├── AccountDto.java
    │   └── CreateAccountCommand.java
    ├── usecases/
    │   ├── CreateAccountUseCase.java
    │   ├── GetAccountUseCase.java
    │   └── GetAccountBalanceUseCase.java
    ├── mappers/
    │   └── AccountApplicationMapper.java
    └── policies/
        └── AccountValidationPolicy.java
```

### Cross-Entity Use Cases
Para use cases que involucran múltiples entidades:

```java
// En application/transaction/usecases/
@ApplicationScoped
public class TransferBetweenAccountsUseCase {
    
    // Inyecta services de diferentes entidades
    @Inject TransactionRepositoryPort transactionRepo;
    @Inject AccountRepositoryPort accountRepo; // Port from account domain
    
    public Transaction execute(String fromAccount, String toAccount, BigDecimal amount) {
        // 1. Validate accounts exist (using account domain)
        // 2. Validate balances (using account domain)  
        // 3. Create transaction (using transaction domain)
        // 4. Update balances (using account domain)
    }
}
```

## 🧪 Testing Strategy

### Use Case Testing
```java
@QuarkusTest
class CreateTransactionUseCaseTest {
    
    @Inject CreateTransactionUseCase useCase;
    
    @InjectMock TransactionRepositoryPort repository;
    @InjectMock IdGeneratorPort idGenerator;
    
    @Test
    void shouldCreatePendingTransaction() {
        // Given
        given(idGenerator.generateId()).willReturn("tx-123");
        given(repository.save(any())).willAnswer(invocation -> invocation.getArgument(0));
        
        // When
        Transaction result = useCase.execute(
            new BigDecimal("100.00"), "USD", "ACC-1", "ACC-2"
        );
        
        // Then
        assertThat(result.getStatus()).isEqualTo(PENDING);
        assertThat(result.getId()).isEqualTo("tx-123");
        verify(repository).save(any(Transaction.class));
    }
}
```

### Integration Testing
```java
@QuarkusTest
class TransactionApplicationIntegrationTest {
    
    @Test
    void shouldCompleteFullTransactionFlow() {
        // Test complete flow from use case to repository
    }
}
```

## 📈 Beneficios de esta Organización

### ✅ **Business-Focused Use Cases**
- Use cases reflejan operaciones de negocio reales
- Fácil entender qué hace cada caso de uso
- Mapping directo con user stories

### ✅ **Testability**  
- Use cases fáciles de testear aisladamente
- Mocking claro de dependencies
- Integration tests por entidad

### ✅ **Maintainability**
- Transaction use cases todos juntos
- Changes localized por entidad
- Clear dependencies entre layers

### ✅ **Team Scalability**
- Team A: `application/transaction/`
- Team B: `application/account/` 
- Minimal conflicts entre teams

---

**Esta application layer** actúa como el **orchestrator** entre el domain rico y la infrastructure, manteniendo los use cases organizados por entidades de negocio para máxima claridad y mantenibilidad.
