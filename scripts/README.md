# Scripts

This directory contains utility scripts for the Quarkus Starter Template project.

## Available Scripts

### `test-api.sh`
Comprehensive API testing script that validates all transaction endpoints:
- Health check validation
- POST /api/v1/transactions (create transaction)
- GET /api/v1/transactions/{id} (get specific transaction)
- GET /api/v1/transactions (list transactions)

**Usage:**
```bash
./scripts/test-api.sh
```

**Prerequisites:** Application must be running on port 8081

### `test-start.sh`
Application startup validation script that:
- Builds the application
- Runs unit tests
- Tests dev mode startup
- Validates health endpoints

**Usage:**
```bash
./scripts/test-start.sh
```

**Prerequisites:** Maven wrapper (./mvnw) must be available

## Running Scripts

All scripts should be executed from the project root directory:

```bash
# Make scripts executable (first time only)
chmod +x scripts/*.sh

# Run API tests
./scripts/test-api.sh

# Test application startup
./scripts/test-start.sh
```
