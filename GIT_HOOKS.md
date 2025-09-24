# 🔧 Git Hooks Configuration

This project includes a pre-commit hook that ensures code quality before commits.

## What the Pre-commit Hook Does

The hook automatically runs these checks before every commit:

1. **📦 Compilation Check**: Ensures code compiles (`./mvnw compile`)
2. **🧪 Test Execution**: Runs all tests (`./mvnw test`)
3. **🎨 Code Formatting**: Checks code style (`./mvnw spotless:check`)
4. **📄 Code Quality**: Detects common issues:
   - TODO/FIXME/XXX comments in staged files
   - Debug statements (`System.out.println`, `printStackTrace`)
   - Large files (>1MB)

## How It Works

The hook is **automatically active** and will:
- ✅ **Allow commit** if all checks pass
- ❌ **Block commit** if critical issues are found (compilation/tests/formatting)
- ⚠️ **Show warnings** for code quality issues but allow commit

## Managing the Hook

Use the management script for hook control:

```bash
# Check hook status
./scripts/git-hooks.sh status

# Test hook manually (without committing)
./scripts/git-hooks.sh test

# Temporarily disable hook
./scripts/git-hooks.sh disable

# Re-enable hook
./scripts/git-hooks.sh enable
```

## Quick Fixes

If the pre-commit hook fails:

### Compilation Issues
```bash
./mvnw compile
# Fix any compiler errors shown
```

### Test Failures
```bash
./mvnw test
# Fix failing tests
```

### Code Formatting Issues
```bash
# Auto-fix formatting
./mvnw spotless:apply

# Or check what needs fixing
./mvnw spotless:check
```

## Emergency Override

In rare cases where you need to bypass the hook:
```bash
git commit --no-verify -m "emergency commit"
```

⚠️ **Use sparingly** - this skips all quality checks!

## Benefits

- 🛡️ **Prevents broken code** from entering the repository
- 🚀 **Maintains consistent code quality** across the team
- 📈 **Reduces CI/CD failures** by catching issues early
- 🎯 **Fast feedback loop** - catches issues in seconds, not minutes

The hook is designed to be **fast and practical** - most checks complete in under 10 seconds for typical commits.
