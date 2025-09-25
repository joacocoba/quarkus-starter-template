# 🧪 Scripts de Testing - Automatización de Pruebas

Scripts para testing automático y validación de la API.

## 📁 Scripts Disponibles

### 🚀 `test-api.sh` - Testing Completo de la API

**Ejecuta todas las pruebas de la API de forma automática y organizada**

#### 🎯 ¿Qué hace?

Este script es el **"test runner completo"** del proyecto. Ejecuta y valida:

- ✅ **Unit tests** con JUnit 5
- ✅ **Integration tests** con TestContainers
- ✅ **API tests** con REST Assured  
- ✅ **Health checks** de endpoints
- ✅ **Smoke tests** de funcionalidad crítica

#### ⚡ Uso Rápido

```bash
# Desde la raíz del proyecto
./scripts/testing/test-api.sh
```

#### 📋 Qué verás durante la ejecución

```bash
🧪 Iniciando tests de la API...

🏗️  [BUILD] Compilando proyecto...
✅ Build exitoso en 45s

🚀 [STARTUP] Iniciando aplicación en modo test...
📡 API disponible en http://localhost:8080
✅ Health check passed

🧪 [UNIT] Ejecutando unit tests...
✅ 45 tests passed (0 failures)

🔗 [INTEGRATION] Ejecutando integration tests...  
✅ 12 tests passed (0 failures)

🌐 [API] Ejecutando API tests...
✅ GET /api/transactions: 200 OK
✅ POST /api/transactions: 201 Created  
✅ GET /api/transactions/404: 404 Not Found ← ¡Esto es bueno!
✅ Error handling: Proper error responses
✅ 8 API tests passed

🎯 [SMOKE] Ejecutando smoke tests...
✅ Application starts correctly
✅ Database connection working
✅ All endpoints responding

🎉 ¡Todos los tests pasaron exitosamente!

📊 Resumen:
• Unit tests: 45/45 ✅
• Integration: 12/12 ✅  
• API tests: 8/8 ✅
• Smoke tests: 4/4 ✅
• Total: 69/69 ✅

⏱️ Tiempo total: 2m 15s
```

#### 🔧 Características Principales

**🔍 Testing Inteligente:**
- Detecta si la aplicación está corriendo
- Usa puerto de test dinámico (TEST_PORT=0)
- Limpia datos de test automáticamente

**🛠️ Validaciones Completas:**
- Tests de unidad con cobertura
- Tests de integración con base de datos real
- Tests de API con casos edge
- Validación de respuestas HTTP correctas

**⚡ Reportes Detallados:**
- Output colorizado y organizado
- Métricas de tiempo y rendimiento
- Identificación clara de fallos

#### 🎯 Tipos de Tests

**🧪 Unit Tests:**
```bash
# Tests de lógica de negocio
• TransactionService
• TransactionMapper  
• ValidationUtils
• Business rules
```

**🔗 Integration Tests:**
```bash
# Tests con dependencias reales
• Database operations
• Repository layer
• Data persistence
• Transaction boundaries
```

**🌐 API Tests:**
```bash
# Tests de endpoints HTTP
• GET /api/transactions → 200 OK
• POST /api/transactions → 201 Created
• GET /api/transactions/nonexistent → 404 Not Found
• Invalid data → 400 Bad Request
• Server errors → 500 Internal Server Error
```

**🎯 Smoke Tests:**
```bash
# Tests de funcionalidad crítica
• Application starts
• Health endpoint responds
• Database connectivity
• Critical user flows
```

#### 🛠️ Opciones Avanzadas

```bash
# Solo unit tests
./scripts/testing/test-api.sh --unit-only

# Solo integration tests
./scripts/testing/test-api.sh --integration-only

# Solo API tests
./scripts/testing/test-api.sh --api-only

# Con coverage report
./scripts/testing/test-api.sh --coverage

# Tests específicos
./scripts/testing/test-api.sh --test TransactionServiceTest

# Modo verbose
./scripts/testing/test-api.sh --verbose

# Modo watch (re-ejecuta en cambios)
./scripts/testing/test-api.sh --watch
```

#### 📋 Casos de Uso Específicos

**🎯 Desarrollo continuo:**
```bash
# En otra terminal, mientras desarrollas
./scripts/testing/test-api.sh --watch
# Se ejecuta automáticamente cuando cambias código
```

**🔄 Pre-commit validation:**
```bash
# Antes de hacer commit
./scripts/testing/test-api.sh --fast
# Solo tests críticos, < 1 minuto
```

**📊 Coverage report:**
```bash
./scripts/testing/test-api.sh --coverage
# Genera reporte en target/site/jacoco/
open target/site/jacoco/index.html
```

**🐛 Debug mode:**
```bash
./scripts/testing/test-api.sh --debug
# Para debuggear tests que fallan
```

#### 🎯 Cuándo usarlo

**✅ Perfecto para:**
- **Pre-commit** validation
- **CI/CD pipelines** 
- **Development workflow**
- **API regression testing**
- **Quality assurance**

**🔄 Frecuencia recomendada:**
- **Cada cambio**: Unit tests
- **Pre-commit**: Full test suite
- **Pre-deployment**: Con coverage
- **Post-deployment**: Smoke tests

#### 🔍 Troubleshooting

**❌ "Application not responding"**
```bash
# Verificar que no hay otra instancia corriendo
lsof -ti:8080 | xargs kill -9

# Ejecutar con debug
./scripts/testing/test-api.sh --debug
```

**❌ "Database connection failed"**
```bash
# Tests usan H2 in-memory, no deberían fallar
# Si falla, verificar:
./mvnw clean test -Dtest=DatabaseTest
```

**❌ "404 test failing"**
```bash
# Si el test de 404 falla (devuelve 500):
# Verificar que GlobalExceptionMapper está en el classpath
curl -v http://localhost:8080/api/transactions/nonexistent
```

**❌ "Tests timeout"**
```bash
# Aumentar timeout para tests lentos
export TEST_TIMEOUT=300
./scripts/testing/test-api.sh
```

**❌ "Port already in use"**
```bash
# Tests usan TEST_PORT=0 (puerto dinámico)
# Si hay conflicto:
export TEST_PORT=9999
./scripts/testing/test-api.sh
```

#### 📊 Integración con IDE

**VS Code:**
```bash
# Configurar tasks.json para ejecutar desde IDE
{
  "label": "Run API Tests",
  "type": "shell", 
  "command": "./scripts/testing/test-api.sh",
  "group": "test"
}
```

**IntelliJ:**
```bash
# Configurar como External Tool
# Program: ./scripts/testing/test-api.sh
# Working directory: $ProjectFileDir$
```

#### 🎯 Testing en CI/CD

**GitHub Actions:**
```yaml
- name: Run API Tests
  run: |
    chmod +x ./scripts/testing/test-api.sh
    ./scripts/testing/test-api.sh --ci
    
- name: Upload Coverage
  run: |
    ./scripts/testing/test-api.sh --coverage
    # Upload target/site/jacoco/ to coverage service
```

**Jenkins:**
```groovy
stage('API Tests') {
    steps {
        sh './scripts/testing/test-api.sh --ci --junit-report'
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/site/jacoco/',
                reportFiles: 'index.html',
                reportName: 'Coverage Report'
            ])
        }
    }
}
```

## 🎯 Flujo de Testing Recomendado

### 🌅 Desarrollo diario

```bash
# 1. Ejecutar tests rápidos mientras desarrollas
./scripts/testing/test-api.sh --unit-only

# 2. Antes de commit, tests completos
./scripts/testing/test-api.sh

# 3. Si todos pasan, hacer commit
git add .
git commit -m "feat: nueva funcionalidad"
```

### 🔄 Pre-deployment

```bash
# 1. Tests completos con coverage
./scripts/testing/test-api.sh --coverage

# 2. Revisar coverage report
open target/site/jacoco/index.html

# 3. Si coverage > 80%, deploy
```

### 🎛️ Debug de fallos

```bash
# 1. Ejecutar con verbose
./scripts/testing/test-api.sh --verbose --debug

# 2. Ejecutar test específico que falla
./scripts/testing/test-api.sh --test TransactionServiceTest

# 3. Debug en IDE si necesario
```

## 💡 Tips Pro

1. **⚡ Tests rápidos**: `--unit-only` para feedback inmediato
2. **🎯 Tests específicos**: `--test NombreDelTest` para debugging
3. **📊 Coverage tracking**: `--coverage` para métricas de calidad
4. **🔍 Watch mode**: `--watch` para TDD workflow
5. **🚀 CI mode**: `--ci` para pipelines automáticos

---

*🧪 Este script es tu mejor amigo para mantener la calidad del código. ¡Úsalo frecuentemente!*
