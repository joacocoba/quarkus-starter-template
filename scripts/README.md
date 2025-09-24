# Scripts Directory

This directory contains utility scripts for the Quarkus Starter Template project.

## Available Scripts

### `test-api.sh`
**Purpose**: Manual API testing script to validate all endpoints

**Usage**:
```bash
# Start the application first
./mvnw quarkus:dev

# In another terminal, run the API tests
./scripts/test-api.sh
```

**What it tests**:
- Health check endpoint
- POST /api/v1/transactions (create)
- GET /api/v1/transactions/{id} (get by ID)  
- GET /api/v1/transactions (list all)

**Requirements**: 
- Application running on localhost:8081
- `curl` and `jq` installed (jq is optional, for pretty JSON formatting)

### `git-hooks.sh`
**Purpose**: Manage Git pre-commit hooks for code quality

**Usage**:
```bash
# Check hook status
./scripts/git-hooks.sh status

# Test the pre-commit hook manually
./scripts/git-hooks.sh test

# Temporarily disable the hook
./scripts/git-hooks.sh disable

# Re-enable the hook
./scripts/git-hooks.sh enable
```

**Pre-commit checks**:
- ✅ Compilation check (`./mvnw compile`)
- ✅ Test execution (`./mvnw test`)
- ✅ Code formatting (`./mvnw spotless:check`)
- ✅ Common issues detection (TODOs, debug statements, large files)

## Notes
- All debug and temporary scripts have been removed
- Only essential, reusable scripts are kept
- Use Maven commands (`./mvnw test`, `./mvnw compile`) for build operations
