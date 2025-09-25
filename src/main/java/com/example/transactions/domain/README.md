# 🏗️ Domain Layer - DDD por Entidades

Este directorio contiene la **lógica de negocio pura** organizada por entidades de dominio siguiendo principios de Domain-Driven Design (DDD).

## 📁 Estructura por Entidades

```
domain/
├── transaction/              # Dominio Transaction
│   ├── model/               # Entidades y Value Objects
│   ├── ports/               # Interfaces del dominio  
│   └── exceptions/          # Excepciones específicas
└── shared/                  # Componentes compartidos
    ├── exceptions/          # Excepciones base
    ├── ports/               # Puertos compartidos
    ├── events/              # Domain Events (preparado)
    └── valueobjects/        # Value Objects compartidos (preparado)
```

## 🎯 Transaction Domain

### 📦 `transaction/model/`
**Entidades y Value Objects del dominio Transaction**

- **`Transaction.java`** - Aggregate Root principal
  - Controla la consistencia del aggregate
  - Factory methods para creación (`createPending()`)
  - Business methods (`complete()`, `fail()`, `isFinal()`)
  - Inmutable con builder pattern

- **`TransactionStatus.java`** - Value Object para estados
  - `PENDING`, `COMPLETED`, `FAILED`
  - Representa el ciclo de vida de la transacción

**Principios**:
- **Rich Domain Model**: Lógica de negocio dentro de las entidades
- **Immutability**: Entidades inmutables con métodos de transformación
- **Domain Logic**: Validaciones y reglas de negocio en el dominio

### 🔌 `transaction/ports/`
**Interfaces que definen las necesidades del dominio**

- **`TransactionRepositoryPort.java`** - Puerto para persistencia
  - `save(Transaction)`, `findById(String)`, `findAll()`
  - Abstrae la tecnología de persistencia
  - Implementado en la capa de infrastructure

**Principios**:
- **Dependency Inversion**: El dominio define lo que necesita
- **Technology Agnostic**: Sin dependencias tecnológicas
- **Interface Segregation**: Interfaces específicas por responsabilidad

### ⚡ `transaction/exceptions/`
**Excepciones específicas del dominio Transaction**

- **`TransactionNotFoundException.java`** - Transacción no encontrada
  - Hereda de `DomainException` (shared)
  - Error code específico: `TRANSACTION_NOT_FOUND`

**Principios**:
- **Explicit Errors**: Errores explícitos del dominio
- **Meaningful Names**: Nombres que reflejan el problema de negocio
- **Domain Specific**: Excepciones específicas por entidad

## 🔄 Shared Domain

### 🚨 `shared/exceptions/`
**Base para todas las excepciones del dominio**

- **`DomainException.java`** - Excepción base abstracta
  - Incluye `errorCode` para identificación
  - Base para todas las excepciones de dominio
  - Consistent error handling across entities

### 🔌 `shared/ports/`
**Puertos compartidos entre entidades**

- **`IdGeneratorPort.java`** - Generación de IDs únicos
  - Usado por múltiples agregates
  - Implementado como UUID en infrastructure

### 📡 `shared/events/` (Preparado)
**Domain Events para comunicación entre bounded contexts**

```java
// Futuro: Domain Events
public interface TransactionCompletedEvent extends DomainEvent {
    String getTransactionId();
    BigDecimal getAmount();
    // ... event data
}
```

### 💎 `shared/valueobjects/` (Preparado)  
**Value Objects compartidos entre entidades**

```java
// Futuro: Value Objects compartidos
public class Money {
    private final BigDecimal amount;
    private final Currency currency;
    // ... value object logic
}
```

## 🎯 Principios DDD Implementados

### 1. **Ubiquitous Language**
- Clases y métodos reflejan el lenguaje del negocio
- `Transaction.complete()` vs `Transaction.setStatus(COMPLETED)`
- Nombres que los expertos del dominio entienden

### 2. **Bounded Contexts**  
- Cada entidad (`transaction/`) es un bounded context
- Contextos claramente delimitados
- Minimal coupling entre contextos

### 3. **Aggregate Design**
- `Transaction` es un Aggregate Root
- Controla consistency dentro del aggregate
- Repository pattern per aggregate

### 4. **Domain-First**
- Domain objects no dependen de infraestructura
- Lógica de negocio encapsulada en el dominio
- Technology decisions are secondary

## 🚀 Escalabilidad

### Nuevas Entidades
Para agregar una nueva entidad (ej. `Account`):

```
domain/
├── transaction/             # ✅ Existente
└── account/                 # 🆕 Nueva entidad
    ├── model/
    │   ├── Account.java     # Aggregate Root
    │   └── AccountType.java # Value Object
    ├── ports/
    │   └── AccountRepositoryPort.java
    └── exceptions/
        └── AccountNotFoundException.java
```

### Microservicios
Cada directorio `domain/transaction/` está preparado para ser extraído como un microservicio independiente.

## 🧪 Testing Strategy

### Domain Testing
```java
// Transaction domain tests
@Test
void shouldCreatePendingTransaction() {
    // Given
    Transaction transaction = Transaction.createPending(/*...*/);
    
    // Then
    assertThat(transaction.getStatus()).isEqualTo(PENDING);
    assertThat(transaction.isFinal()).isFalse();
}

@Test
void shouldCompleteTransaction() {
    // Given
    Transaction pending = Transaction.createPending(/*...*/);
    
    // When
    Transaction completed = pending.complete();
    
    // Then
    assertThat(completed.getStatus()).isEqualTo(COMPLETED);
    assertThat(completed.isFinal()).isTrue();
}
```

**Testing Principles**:
- **Pure Unit Tests**: Test domain logic without frameworks
- **Business Scenarios**: Tests reflect business requirements
- **Fast Feedback**: Domain tests should be extremely fast

## 📈 Beneficios de esta Organización

### ✅ **Alta Cohesión**
Todo lo relacionado con `Transaction` domain está junto:
- Model, Ports, Exceptions en un solo lugar
- Fácil entender el dominio completo
- Changes localized to entity

### ✅ **Bajo Acoplamiento**  
- Entities independientes entre sí
- Shared components para cross-cutting concerns
- Clear boundaries between domains

### ✅ **Preparación para Microservicios**
- `domain/transaction/` = microservicio domain
- Clear interfaces through ports
- Minimal external dependencies

### ✅ **Mantenibilidad**
- Business logic clearly organized
- Easy to find transaction-related code  
- Intuitive structure for developers

---

**Esta estructura de dominio** convierte el código de "capas técnicas" a "**modelo de negocio**", facilitando el entendimiento y mantenimiento del sistema desde la perspectiva del negocio.
