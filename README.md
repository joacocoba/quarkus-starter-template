# Tra# Quarkus Starter Template

A production-ready Quarkus starter template implementing Hexagonal Architecture (Ports & Adapters) pattern with a complete transaction management example. This template demonstrates best practices for building enterprise-grade microservices.

## 🚀 Quick Start

```bash
# Clone this template
git clone <your-repo-url>
cd quarkus-starter-template

# Build the application
./mvnw clean compile

# Run tests
./mvnw test

# Start development mode
./mvnw quarkus:dev
```

Access the application at:
- **API Base**: http://localhost:8081/api/v1/transactions
- **Swagger UI**: http://localhost:8081/q/swagger-ui
- **Health Check**: http://localhost:8081/q/health

## 📋 What's Included

✅ **Modern Technology Stack**
- Quarkus 3.15.1 with Java 21
- Maven wrapper for consistent builds
- Production-ready configuration

✅ **Hexagonal Architecture**
- Clean separation of concerns
- Domain-driven design principles
- Testable and maintainable code structure

✅ **Complete Transaction API**
- Create, read, and list transactions
- Account number tracking (origin/destination)
- Comprehensive validation
- RESTful endpoints with proper HTTP codes

✅ **Enterprise Features**
- OpenAPI/Swagger documentation
- Health checks and readiness probes
- Metrics and observability
- CORS configuration
- Comprehensive error handling

✅ **Quality Gates**
- Code formatting (Spotless + Google Java Format)
- Static analysis (Checkstyle)
- Dependency management (Maven Enforcer)
- Unit testing with high coverage

✅ **Developer Experience**
- Hot reload in development
- Comprehensive test utilities
- Docker support
- Helpful scripts for testing

## 🎯 API Examples

### Create Transaction
```bash
curl -X POST http://localhost:8081/api/v1/transactions 
  -H "Content-Type: application/json" 
  -d '{
    "amount": 150.75,
    "currency": "USD",
    "originAccountNumber": "ACC-123456789",
    "destinationAccountNumber": "ACC-987654321"
  }'
```

### Get Transaction
```bash
curl http://localhost:8081/api/v1/transactions/{transaction-id}
```

### List Transactions
```bash
curl "http://localhost:8081/api/v1/transactions?offset=0&limit=20"
```

## 🏗️ Architecture

```
src/main/java/com/example/transactions/
├── domain/                     # 🎯 Core business logic
│   ├── model/                 #    Domain entities & value objects
│   └── ports/                 #    Interfaces for external dependencies
├── application/               # 🔄 Use cases & orchestration
│   ├── dto/                  #    Application data transfer objects
│   └── usecases/             #    Business use cases
├── infrastructure/           # 🔌 External adapters
│   ├── repositories/         #    Data persistence implementations
│   └── services/            #    External service integrations
├── presentation/            # 🌐 API layer
│   ├── dto/                 #    Request/response DTOs
│   └── rest/                #    REST controllers
└── shared/                  # 🛠️ Cross-cutting concerns
    └── constants/           #    Application constants
```

## 🧪 Testing

The template includes comprehensive testing utilities:

```bash
# Run all tests
./mvnw test

# Run with coverage
./mvnw test jacoco:report

# Test the running application
./scripts/test-api.sh

# Test application startup
./scripts/test-start.sh
```

## 🔧 Customization

### 1. Change Package Structure
Update package names from `com.example.transactions` to your domain:
- Update all Java files
- Update `pom.xml` group and artifact IDs
- Update application properties

### 2. Adapt Domain Model
Replace the `Transaction` entity with your business domain:
- Modify `Transaction.java` in the domain layer
- Update corresponding DTOs and use cases
- Adjust validation rules

### 3. Add Database Integration
The template uses in-memory storage. To add database support:
- Uncomment database dependencies in `pom.xml`
- Implement repository adapters with JPA
- Add database configuration

### 4. Security Integration
Add authentication and authorization:
- Include Quarkus Security extensions
- Configure JWT or OAuth2
- Add security annotations to endpoints

## 🐳 Docker Support

```bash
# Build native image
./mvnw clean package -Dnative

# Build Docker image
docker build -t quarkus-starter-template .

# Run container
docker run -p 8081:8081 quarkus-starter-template
```

## 📊 Quality Metrics

The template enforces high code quality:
- **Code formatting**: Automatically applied via Spotless
- **Style checks**: Google Java Style via Checkstyle
- **Dependency management**: Strict version control
- **Test coverage**: Comprehensive unit and integration tests

## 🚀 Production Readiness

This template is designed for production use:
- **Health checks**: Readiness and liveness probes
- **Metrics**: Application and JVM metrics
- **Observability**: Structured logging and tracing ready
- **Security**: CORS configured, validation in place
- **Performance**: Optimized for Quarkus native compilation

## 📚 Next Steps

1. **Customize the domain**: Replace transaction logic with your business needs
2. **Add persistence**: Implement database integration
3. **Enhance security**: Add authentication and authorization
4. **Monitoring**: Set up logging, metrics, and alerting
5. **CI/CD**: Configure automated testing and deployment

## 🤝 Contributing

1. Fork this template
2. Create your feature branch
3. Ensure all quality gates pass: `./mvnw verify`
4. Submit a pull request

## 📄 License

This template is provided as-is for educational and commercial use.

---

**Happy coding! 🎉**

This template gives you a solid foundation to build robust, scalable microservices with Quarkus and clean architecture principles.ce

A production### API Endpoints

```bash
# Create a transaction
curl -X POST http://localhost:8081/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100.50,
    "currency": "USD",
    "originAccountNumber": "ACC-12345678",
    "destinationAccountNumber": "ACC-87654321"
  }'

# Get a transaction by ID
curl http://localhost:8081/api/v1/transactions/{id}

# List all transactions with pagination
curl "http://localhost:8081/api/v1/transactions?offset=0&limit=10"
```15.1 microservice demonstrating Hexagonal Architecture (Ports & Adapters) pattern for transaction management.

## 🚀 Quick Start

The template is now **ready to use**! The CDI ambiguous dependency issue has been resolved.

### Build & Test
```bash
# Build the application
./mvnw clean compile

# Run unit tests
./mvnw test

# Run all quality checks
./mvnw verify

# Start development mode
./mvnw quarkus:dev
```

### ⚠️ CDI Configuration Fix Applied

**Issue**: The template initially had both `InMemoryTransactionRepositoryAdapter` and `OracleTransactionRepositoryAdapter` as CDI beans, causing ambiguous dependency injection.

**Solution**: The `OracleTransactionRepositoryAdapter` has been disabled (commented out `@ApplicationScoped`) as it's a placeholder implementation. This allows the `InMemoryTransactionRepositoryAdapter` to work properly for development and testing.

### Access Points

- **Application**: http://localhost:8081 (dev mode)
- **Health Check**: http://localhost:8081/q/health
- **OpenAPI/Swagger**: http://localhost:8081/q/swagger-ui
- **Metrics**: http://localhost:8081/q/metrics

### API Endpoints

```bash
# Create a transaction
curl -X POST http://localhost:8081/api/v1/transactions 
  -H "Content-Type: application/json" 
  -d '{
    "amount": 100.50,
    "description": "Test transaction"
  }'

# Get a transaction by ID
curl http://localhost:8081/api/v1/transactions/{id}

# List all transactions with pagination
curl "http://localhost:8081/api/v1/transactions?page=0&size=10"
```

## 📋 Features Implemented

✅ **Complete Hexagonal Architecture**
- Domain models with business logic encapsulation
- Ports (interfaces) for external dependencies
- Adapters for infrastructure concerns
- Use cases for application logic

✅ **Production-Ready Configuration**  
- Quarkus 3.15.1 with Java 21
- Quality gates: Spotless + Checkstyle + Enforcer
- Health checks and metrics
- OpenAPI documentation
- CORS configuration

✅ **Repository Pattern**
- In-memory repository (active for dev/test)
- Oracle repository placeholder (disabled)
- Configurable via `app.repository.type` property

✅ **Docker Support**
- Multi-stage Dockerfile with Red Hat UBI 9
- Non-root user for security
- Health checks and proper signals

✅ **Testing Infrastructure**
- Unit tests with JUnit 5
- REST Assured for API testing
- AssertJ for fluent assertions

## 🏗️ Architecture

```
src/main/java/com/example/transactions/
├── domain/                     # Core business logic
│   ├── model/                 # Domain entities
│   └── ports/                 # Interfaces (repository, services)
├── application/               # Use cases & orchestration  
│   ├── dto/                  # Data transfer objects
│   └── usecases/             # Application services
├── infrastructure/           # External adapters
│   ├── repositories/         # Data persistence
│   └── services/            # External services
├── presentation/            # REST controllers
│   └── rest/
└── shared/                 # Cross-cutting concerns
    └── pagination/
```

## 🔧 Configuration

### Repository Selection
The application uses the in-memory repository by default. To switch to Oracle (when implemented):

```properties
app.repository.type=oracle  # Change to 'oracle' when ready
```

### Enabling Oracle Repository
When ready to implement Oracle persistence:

1. Uncomment the `@ApplicationScoped` annotation in `OracleTransactionRepositoryAdapter.java`
2. Set `app.repository.type=oracle` in application properties
3. Add Oracle dependencies in `pom.xml`
4. Implement the actual database operations

## 🧪 Quality Gates

The project includes strict quality enforcement:

- **Code formatting**: Google Java Format via Spotless
- **Code style**: Checkstyle with Google rules
- **Dependency management**: Maven Enforcer with Java 21 requirements
- **Testing**: Minimum test coverage expectations

## 🔍 Troubleshooting

### Common Issues

**Port 8080 in use**: The dev configuration uses port 8081 to avoid conflicts.

**CDI Ambiguous Dependencies**: This has been resolved by disabling the Oracle adapter placeholder.

**Quality checks failing**: Run `./mvnw spotless:apply` to auto-format code.

## 📚 Next Steps

1. **Database Integration**: Implement the Oracle adapter with JPA entities
2. **Security**: Add authentication and authorization
3. **Observability**: Enhance logging and distributed tracing
4. **CI/CD**: Set up GitHub Actions or similar pipeline
5. **Performance**: Add caching and optimize queries

---

**Status**: ✅ **Ready for Development**

The template is fully functional with the CDI issue resolved. You can now run `./mvnw quarkus:dev` and start developing your transaction service!
