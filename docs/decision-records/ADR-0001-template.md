# ADR-0001: Hexagonal Architecture Template

**Status**: Accepted  
**Date**: 2025-09-24  
**Authors**: Development Team

## Context

We need to create a production-grade microservices template that:
- Ensures clean separation of business logic from technical concerns
- Supports multiple database implementations (in-memory, Oracle)
- Provides excellent testability and maintainability
- Follows enterprise-grade quality standards
- Can evolve incrementally without major refactoring

## Decision

We will adopt **Hexagonal Architecture** (Ports & Adapters) as the primary architectural pattern for this template.

### Key Architectural Decisions

#### 1. Layer Structure
```
domain/           # Pure business logic (no framework dependencies)
├── model/        # Domain entities and value objects
└── ports/        # Interfaces for external dependencies

application/      # Use cases and orchestration
├── usecases/     # Business use case implementations
└── dto/          # Application-level data transfer objects

infrastructure/   # Technical implementations
├── repositories/ # Data access implementations
└── services/     # External service adapters

presentation/     # API controllers and DTOs
├── rest/         # REST API resources
└── dto/          # API request/response objects

shared/           # Common utilities
├── errors/       # Domain exceptions
└── result/       # Functional result types
```

#### 2. Dependency Direction
- **Inward Dependencies Only**: All dependencies point toward the domain
- **Interface Segregation**: Small, focused interfaces (ports)
- **Dependency Injection**: CDI for wiring implementations

#### 3. Technology Stack
- **Framework**: Quarkus 3.20 (cloud-native, fast startup)
- **Language**: Java 21 (modern language features, sealed classes)
- **Build**: Maven with strict quality gates
- **Runtime**: JVM mode with Red Hat UBI containers

#### 4. Quality Standards
- **Code Formatting**: Spotless with Google Java Format
- **Static Analysis**: Checkstyle with strict rules
- **Dependency Management**: Maven Enforcer plugin
- **Testing**: JUnit 5 + REST Assured + AssertJ

## Rationale

### Why Hexagonal Architecture?

1. **Business Logic Protection**: Domain layer is completely isolated from technical concerns
2. **Testing**: Easy to test business logic without mocking frameworks
3. **Flexibility**: Can swap implementations (in-memory ↔ Oracle) without code changes
4. **Evolution**: New features can be added without affecting existing code
5. **Team Productivity**: Clear boundaries reduce coordination overhead

### Why Quarkus 3.20?

1. **Cloud Native**: Optimized for containers and Kubernetes
2. **Fast Startup**: Sub-second boot time
3. **Low Memory**: Efficient resource usage
4. **Developer Experience**: Live reload, dev services
5. **Standards**: Jakarta EE standards compliance
6. **Ecosystem**: Rich extension ecosystem

### Why Java 21?

1. **Performance**: JVM optimizations and garbage collector improvements
2. **Language Features**: Records, sealed classes, pattern matching
3. **Long-term Support**: LTS version with enterprise backing
4. **Ecosystem Maturity**: All dependencies support Java 21

## Implementation Guidelines

### 1. Domain Layer Rules
- **No Framework Dependencies**: Pure Java only
- **Rich Domain Models**: Business logic belongs in entities
- **Immutable Where Possible**: Use records and final fields
- **Validation in Constructors**: Fail fast with meaningful errors

### 2. Application Layer Rules
- **Thin Orchestration**: Coordinate domain objects and ports
- **Single Responsibility**: One use case per class
- **Transaction Boundaries**: Define transaction scope
- **Error Handling**: Convert technical to domain exceptions

### 3. Infrastructure Layer Rules
- **Implement Ports**: All external dependencies through ports
- **Configuration-Driven**: Support multiple implementations
- **Error Translation**: Convert technical to domain exceptions
- **Resource Management**: Proper connection and resource handling

### 4. Presentation Layer Rules
- **Thin Controllers**: Delegate to use cases immediately
- **Input Validation**: Validate at API boundary
- **HTTP Semantics**: Proper status codes and headers
- **API Versioning**: Design for future evolution

## Trade-offs

### Advantages
✅ **High Testability**: Easy unit testing of business logic  
✅ **Flexibility**: Easy to swap implementations  
✅ **Maintainability**: Clear separation of concerns  
✅ **Team Scalability**: Well-defined boundaries  
✅ **Evolution**: Incremental enhancement support  

### Disadvantages
❌ **Initial Complexity**: More classes and interfaces  
❌ **Learning Curve**: Team needs to understand hexagonal concepts  
❌ **Potential Over-engineering**: Might be overkill for simple CRUD  

## Consequences

### Positive
- Clean, testable codebase
- Easy to onboard new developers
- Supports complex business logic evolution
- Multiple persistence strategies possible
- Framework-independent business logic

### Negative
- More initial boilerplate code
- Requires discipline to maintain boundaries
- Additional abstraction layers

## Compliance

This ADR will be considered successful when:

1. ✅ Domain layer has zero framework dependencies
2. ✅ All external dependencies go through ports
3. ✅ Business logic is easily unit testable
4. ✅ Multiple repository implementations work seamlessly
5. ✅ New features can be added without modifying existing layers

## References

- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Clean Architecture by Uncle Bob](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Quarkus Architecture Guide](https://quarkus.io/guides/architecture)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
