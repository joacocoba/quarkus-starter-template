# Architecture Overview

## System Design

The Transactions Service is built using **Hexagonal Architecture** (also known as Ports & Adapters), ensuring clean separation of concerns and testability.

## Architecture Layers

### 1. Domain Layer (`domain/`)
**Pure business logic, framework-independent**

- **Models**: `Transaction`, `TransactionStatus`
- **Ports**: `TransactionRepositoryPort`, `IdGeneratorPort`
- **Responsibility**: Encapsulates business rules and domain logic

**Key Principles**:
- No framework dependencies
- Contains all business rules
- Immutable entities where possible
- Rich domain model with behavior

### 2. Application Layer (`application/`)
**Use cases and orchestration**

- **Use Cases**: `CreateTransactionUseCase`, `GetTransactionUseCase`, `ListTransactionsUseCase`
- **DTOs**: `TransactionQuery`
- **Responsibility**: Orchestrates domain objects to fulfill use cases

**Key Principles**:
- Thin orchestration layer
- Coordinates between domain and infrastructure
- Stateless operations
- Transaction boundaries

### 3. Infrastructure Layer (`infrastructure/`)
**Technical implementations**

- **Repositories**: `InMemoryTransactionRepositoryAdapter`, `OracleTransactionRepositoryAdapter`
- **Services**: `UuidGeneratorAdapter`
- **Responsibility**: Implements ports with actual technology

**Key Principles**:
- Implements domain ports
- Contains all framework-specific code
- Configurable implementations
- Technology isolation

### 4. Presentation Layer (`presentation/`)
**API controllers and DTOs**

- **REST Resources**: `TransactionResource`
- **DTOs**: Request/Response objects
- **Exception Handling**: `GlobalExceptionMapper`
- **Responsibility**: Exposes use cases via REST API

**Key Principles**:
- Thin controllers
- Input validation
- Proper HTTP semantics
- API versioning ready

### 5. Shared Layer (`shared/`)
**Common utilities and types**

- **Errors**: Domain exceptions
- **Result Types**: Functional result handling
- **Responsibility**: Reusable components across layers

## Data Flow

```
HTTP Request → Presentation → Application → Domain ← Infrastructure
     ↓              ↓            ↓          ↓         ↓
  REST API → Use Cases → Domain Logic → Ports ← Adapters
```

1. **Inbound**: HTTP → Controller → Use Case → Domain
2. **Outbound**: Domain → Port → Infrastructure Adapter

## Technology Stack

### Core Framework
- **Quarkus 3.20**: Kubernetes-native Java framework
- **Java 21**: Latest LTS with modern language features
- **Maven**: Build automation and dependency management

### Quality & Standards
- **Spotless**: Code formatting (Google Java Format)
- **Checkstyle**: Static code analysis
- **Maven Enforcer**: Dependency and version compliance
- **EditorConfig**: Consistent coding styles

### Testing
- **JUnit 5**: Unit testing framework
- **REST Assured**: API testing
- **AssertJ**: Fluent assertions
- **Mockito**: Mocking framework

### Observability
- **Micrometer**: Metrics collection
- **SmallRye Health**: Health checks
- **Structured Logging**: JSON logging support

### API Documentation
- **OpenAPI 3**: API specification
- **Swagger UI**: Interactive documentation

## Design Patterns

### 1. Hexagonal Architecture
- **Ports**: Define contracts (interfaces)
- **Adapters**: Implement contracts with specific technologies
- **Domain**: Contains core business logic

### 2. Dependency Injection
- **Constructor Injection**: Preferred for required dependencies
- **CDI**: Context and Dependency Injection for wiring

### 3. Repository Pattern
- **Abstract Data Access**: Domain doesn't know about persistence details
- **Multiple Implementations**: In-memory, Oracle, etc.

### 4. Use Case Pattern
- **Single Responsibility**: Each use case handles one business operation
- **Orchestration**: Coordinates domain objects and infrastructure

### 5. DTO Pattern
- **Data Transfer**: Separate DTOs for API and internal objects
- **Validation**: Input validation at boundaries

## Configuration Management

### Profiles
- **dev**: Development with in-memory storage
- **test**: Testing configuration
- **prod**: Production with Oracle database

### Property Sources
- **application.properties**: Default configuration
- **application-{profile}.properties**: Profile-specific overrides
- **Environment Variables**: Runtime configuration

## Security Considerations

### Current Implementation
- **Input Validation**: Jakarta Validation on all inputs
- **Error Handling**: No sensitive information in error responses
- **Non-root Execution**: Docker containers run as non-root user

### Future Enhancements
- JWT authentication
- Role-based authorization
- Rate limiting
- Request/response logging

## Scalability & Performance

### Current Features
- **Stateless Design**: No server-side session state
- **Connection Pooling**: Database connection management
- **Pagination**: Prevents large result sets

### Future Considerations
- Caching layer (Redis)
- Event-driven architecture
- Read replicas for queries
- Circuit breaker pattern

## Monitoring & Observability

### Health Checks
- **Readiness**: `/q/health/ready` - Ready to serve traffic
- **Liveness**: `/q/health/live` - Application is running

### Metrics
- **Micrometer**: JVM and custom metrics
- **Prometheus**: Metrics collection endpoint

### Logging
- **Structured Logs**: JSON format for log aggregation
- **Correlation IDs**: Request tracing
- **Log Levels**: Configurable per package

## Testing Strategy

### Unit Tests
- **Domain Layer**: Pure unit tests, no mocks
- **Application Layer**: Test use cases with mocked ports
- **Infrastructure Layer**: Test adapters with real implementations

### Integration Tests
- **API Tests**: Full HTTP request/response cycle
- **Repository Tests**: Database integration
- **Container Tests**: Docker image testing

### Test Environments
- **In-memory**: Fast tests with in-memory implementations
- **Testcontainers**: Integration tests with real databases
- **CI/CD**: Automated testing pipeline

## Evolution Path

The architecture supports incremental evolution:

1. **Phase 1**: Basic CRUD operations (current)
2. **Phase 2**: Oracle database integration
3. **Phase 3**: Event-driven features (outbox pattern)
4. **Phase 4**: Advanced features (caching, security)
5. **Phase 5**: Microservices patterns (service mesh, distributed tracing)
