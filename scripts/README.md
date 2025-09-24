# 📁 Directorio de Scripts

Este directorio contiene scripts de utilidad para el proyecto del servicio de transacciones.

## 🛠️ Scripts Disponibles

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
# 🛠️ Guía Completa de Scripts - Paso a Paso

Esta carpeta contiene **scripts de automatización** que facilitan el desarrollo, testing y mantenimiento del proyecto. Cada script está diseñado para ser fácil de usar y proporcionar retroalimentación clara.

---

## 📋 Resumen de Scripts Disponibles

| Script | 📝 Propósito | ⚡ Uso Rápido | 🎯 Cuándo usar |
|--------|-------------|--------------|---------------|
| [`setup-dev.sh`](#-setup-devsh---configuración-automática-del-entorno) | Configurar entorno de desarrollo | `./setup-dev.sh` | Primera vez o cambios de Java |
| [`java-env.sh`](#-java-envsh---gestión-inteligente-de-java) | Gestión de versiones de Java | `source ./java-env.sh` | Cambios de versión de Java |
| [`test-api.sh`](#-test-apish---testing-automático-de-la-api) | Testing completo de la API | `./test-api.sh` | Verificar que la API funciona |
| [`git-hooks.sh`](#-git-hookssh---hooks-de-git-para-calidad) | Instalar hooks de calidad | `./git-hooks.sh install` | Primera configuración del repo |

---

## 🚀 setup-dev.sh - Configuración Automática del Entorno

### 🎯 ¿Qué hace?
Configura automáticamente tu entorno de desarrollo con Java 21, verificaciones de herramientas y hooks de Git.

### 🔧 Funcionalidades
- ✅ **Detección automática de SDKMAN** - Instala y configura Java 21
- ✅ **Verificación de herramientas** - Maven, Git, curl
- ✅ **Configuración de hooks** - Pre-commit para calidad de código
- ✅ **Configuración multiplataforma** - macOS, Linux, Windows (WSL)
- ✅ **Mensajes claros** - Retroalimentación detallada de cada paso

### 📚 Uso Paso a Paso

#### **Ejecución Simple**
```bash
# Desde la raíz del proyecto
./scripts/setup-dev.sh
```

#### **Qué verás durante la ejecución:**

```bash
🚀 Configurando entorno de desarrollo...

✅ [JAVA] Java 21.0.4 detectado y configurado
✅ [MAVEN] Maven 3.9.8 disponible  
✅ [GIT] Git configurado correctamente
✅ [HOOKS] Pre-commit hooks instalados

🎉 ¡Entorno configurado exitosamente!

📋 Próximos pasos:
1. Ejecutar: ./mvnw quarkus:dev
2. Abrir: http://localhost:8081/q/swagger-ui
3. Probar: ./scripts/test-api.sh
```

#### **Casos de Uso Específicos**

**🔄 Cuando cambies de versión de Java:**
```bash
# El script detectará el cambio y reconfigurarán todo
./scripts/setup-dev.sh

# Verificar que Java 21 está activo
java -version
```

**🆕 Configuración de nuevo desarrollador:**
```bash
# Primer día en el proyecto - ejecutar una sola vez
git clone <repo>
cd transactions-service
./scripts/setup-dev.sh

# ¡Listo para desarrollar! 
./mvnw quarkus:dev
```

**🐛 Solución de problemas:**
```bash
# Si algo no funciona, ver logs detallados
./scripts/setup-dev.sh --verbose

# Forzar reinstalación completa
./scripts/setup-dev.sh --force
```

### ⚙️ Configuraciones que Aplica

1. **Java Environment**
   - Detecta SDKMAN instalado
   - Activa Java 21 automáticamente
   - Configura `JAVA_HOME`

2. **Maven Configuration**
   - Verifica Maven 3.9+
   - Configura wrapper de Maven

3. **Git Hooks**
   - Instala pre-commit hook
   - Configuración de formato automático

4. **Environment Variables**
   - Crea `.env` desde `.env.example` si no existe

---

## ☕ java-env.sh - Gestión Inteligente de Java

### 🎯 ¿Qué hace?
Proporciona funciones inteligentes para gestionar versiones de Java con SDKMAN, similar a NVM para Node.js.

### 🔧 Funcionalidades
- ✅ **Auto-detección de SDKMAN** - Encuentra e inicializa automáticamente
- ✅ **Gestión de versiones** - Cambia fácilmente entre versiones de Java
- ✅ **Verificación inteligente** - Detecta si Java 21 está disponible
- ✅ **Instalación automática** - Instala Java 21 si no existe
- ✅ **Integración con proyecto** - Lee configuración de `.sdkmanrc`

### 📚 Uso Paso a Paso

#### **Configuración Inicial (una sola vez)**
```bash
# Cargar funciones de Java
source ./scripts/java-env.sh

# O agregar a tu .bashrc/.zshrc para uso permanente
echo "source $(pwd)/scripts/java-env.sh" >> ~/.zshrc
```

#### **Comandos Disponibles**

**🔍 Verificar estado actual:**
```bash
java_env_status
# Output:
# ✅ SDKMAN: Disponible
# ✅ Java 21: Activo (21.0.4-tem)  
# ✅ JAVA_HOME: /Users/.../21.0.4-tem
```

**🎯 Activar Java 21 automáticamente:**
```bash
java_env_setup
# Output:
# 🎯 Activando Java 21 para este proyecto...
# ✅ Java 21.0.4 configurado correctamente
```

**📋 Listar versiones disponibles:**
```bash
java_env_list_versions
# Output:
# Versiones de Java instaladas:
# * 21.0.4-tem (current)
#   17.0.8-tem
#   11.0.20-tem
```

**⬇️ Instalar nueva versión:**
```bash
java_env_install_java21
# Output:
# 📥 Instalando Java 21...
# ✅ Java 21.0.4-tem instalado y configurado
```

#### **Uso en Scripts de CI/CD**
```bash
#!/bin/bash
# En tu script de CI
source ./scripts/java-env.sh
java_env_setup

# Ahora Java 21 está garantizado
mvn clean test
```

### 🔧 Integración con IDEs

**VS Code:**
```json
// En .vscode/settings.json (se configura automáticamente)
{
  "java.jdt.ls.java.home": "/Users/tu-usuario/.sdkman/candidates/java/21.0.4-tem"
}
```

**IntelliJ IDEA:**
```bash
# El script actualiza automáticamente las configuraciones del proyecto
# IntelliJ detectará el cambio automáticamente
```

---

## 🧪 test-api.sh - Testing Automático de la API

### 🎯 ¿Qué hace?
Ejecuta una suite completa de tests de la API REST, verificando todos los endpoints y casos de uso críticos.

### 🔧 Funcionalidades
- ✅ **Tests completos de endpoints** - Todos los métodos HTTP
- ✅ **Verificación de health checks** - Liveness y readiness
- ✅ **Tests de casos edge** - Errores, validaciones, límites
- ✅ **Reportes detallados** - Resultados coloridos y claros
- ✅ **Configuración flexible** - Diferentes entornos y puertos

### 📚 Uso Paso a Paso

#### **Ejecución Básica**
```bash
# La aplicación debe estar corriendo
./mvnw quarkus:dev

# En otra terminal, ejecutar tests
./scripts/test-api.sh
```

#### **Qué verás durante los tests:**

```bash
🧪 Iniciando tests de API...

✅ [HEALTH] Health check endpoint
✅ [HEALTH] Readiness endpoint  
✅ [HEALTH] Liveness endpoint

📊 [API] Testing endpoints de transacciones...
✅ [POST] Crear transacción válida
✅ [POST] Validación de campos requeridos
✅ [GET] Obtener transacción por ID
✅ [GET] Listar todas las transacciones
✅ [GET] Paginación de resultados

🎉 Todos los tests pasaron (8/8)
⏱️ Tiempo total: 3.2 segundos
```

#### **Opciones Avanzadas**

**🎯 Test específico:**
```bash
# Solo health checks
./scripts/test-api.sh --health-only

# Solo endpoints de transacciones
./scripts/test-api.sh --api-only

# Modo verbose (más detalles)
./scripts/test-api.sh --verbose
```

**🌐 Diferentes entornos:**
```bash
# Puerto personalizado
PORT=8082 ./scripts/test-api.sh

# URL completa personalizada
API_URL=http://staging.example.com ./scripts/test-api.sh
```

**📊 Formato de salida:**
```bash
# JSON para integración con CI
./scripts/test-api.sh --format=json

# JUnit XML para reportes
./scripts/test-api.sh --format=junit
```

#### **Casos de Uso Típicos**

**🚀 Desarrollo diario:**
```bash
# Workflow típico
./mvnw quarkus:dev &          # Iniciar en background
sleep 5                       # Esperar que inicie
./scripts/test-api.sh         # Ejecutar tests
fg                           # Volver a la aplicación
```

**🔄 Integración continua:**
```bash
# En pipeline de CI/CD
./mvnw quarkus:run &
SERVER_PID=$!
sleep 10

./scripts/test-api.sh --format=junit
TESTS_EXIT_CODE=$?

kill $SERVER_PID
exit $TESTS_EXIT_CODE
```

**🐛 Debug de problemas:**
```bash
# Tests detallados para debug
./scripts/test-api.sh --verbose --debug

# Test individual fallando
./scripts/test-api.sh --test="create_transaction"
```

### 📋 Tests Incluidos

#### **Health Checks**
- ✅ `/q/health` - Estado general
- ✅ `/q/health/ready` - Listo para tráfico  
- ✅ `/q/health/live` - Aplicación viva

#### **API Endpoints**
- ✅ `POST /api/v1/transactions` - Crear transacción
- ✅ `GET /api/v1/transactions/{id}` - Obtener por ID
- ✅ `GET /api/v1/transactions` - Listar todas
- ✅ `GET /api/v1/transactions?page=0&size=10` - Paginación

#### **Casos Edge**
- ✅ Validación de campos requeridos
- ✅ Manejo de IDs inexistentes  
- ✅ Límites de paginación
- ✅ Formatos de moneda inválidos

---

## 🔗 git-hooks.sh - Hooks de Git para Calidad

### 🎯 ¿Qué hace?
Instala y gestiona hooks de Git que garantizan calidad de código antes de cada commit.

### 🔧 Funcionalidades
- ✅ **Pre-commit hooks** - Verificaciones automáticas antes de commit
- ✅ **Formato de código** - Aplicación automática de Google Java Style
- ✅ **Ejecución de tests** - Tests unitarios antes de commit
- ✅ **Verificación de build** - Compilación exitosa requerida
- ✅ **Gestión flexible** - Instalar, desinstalar, verificar estado

### 📚 Uso Paso a Paso

#### **Instalación (una sola vez por repo)**
```bash
# Instalar hooks de calidad
./scripts/git-hooks.sh install
```

**Qué hace la instalación:**
```bash
🔗 Instalando hooks de Git...

✅ Pre-commit hook instalado
✅ Verificación de formato configurada
✅ Ejecución de tests habilitada  
✅ Build verification activada

🎉 Hooks instalados correctamente!

💡 Ahora cada commit ejecutará:
   - Formato de código (Spotless)
   - Tests unitarios
   - Verificación de build
```

#### **Comandos Disponibles**

**📋 Verificar estado:**
```bash
./scripts/git-hooks.sh status
# Output:
# 🔍 Estado de Git Hooks:
# ✅ Pre-commit: Instalado y activo
# ✅ Formato automático: Habilitado
# ✅ Tests en commit: Habilitado
```

**🗑️ Desinstalar hooks:**
```bash
./scripts/git-hooks.sh uninstall
# Output:
# 🗑️ Hooks de Git desinstalados
# ⚠️ Los commits ya no ejecutarán verificaciones automáticas
```

**🔧 Reinstalar hooks:**
```bash
./scripts/git-hooks.sh reinstall
# Equivale a: uninstall + install
```

**✅ Ejecutar verificaciones manuales:**
```bash
./scripts/git-hooks.sh test
# Ejecuta las mismas verificaciones que el pre-commit hook
```

#### **Qué Sucede en Cada Commit**

Cuando hagas `git commit`, automáticamente se ejecutará:

```bash
🔍 Ejecutando verificaciones pre-commit...

1️⃣ [FORMATO] Aplicando Google Java Style...
   ✅ 15 archivos formateados
   
2️⃣ [COMPILACIÓN] Verificando que compila...
   ✅ Build exitoso
   
3️⃣ [TESTS] Ejecutando tests unitarios...
   ✅ 22/22 tests pasaron
   
4️⃣ [CALIDAD] Verificando reglas Checkstyle...
   ✅ No violations encontradas

🎉 ¡Commit aprobado!
```

**Si algo falla:**
```bash
❌ [TESTS] 2 tests fallaron
❌ [FORMATO] 3 archivos necesitan formato

🚫 Commit rechazado. Por favor corrige:

💡 Para aplicar formato automático:
   ./mvnw spotless:apply

💡 Para ejecutar tests:
   ./mvnw test
```

### 🎛️ Configuración Personalizada

#### **Deshabilitar verificaciones específicas:**
```bash
# Skip tests (solo para casos excepcionales)
git commit --no-verify

# O configurar ambiente
export SKIP_TESTS=true
git commit
```

#### **Configurar qué tests ejecutar:**
```bash
# En .git/hooks/pre-commit (editado por el script)
# Modificar para ejecutar solo tests críticos
./mvnw test -Dtest=*ResourceTest
```

---

## 🚀 Flujo de Trabajo Recomendado

### 🌅 Setup Inicial (nuevo desarrollador)
```bash
# 1. Clonar y configurar
git clone <repo>
cd transactions-service
./scripts/setup-dev.sh

# 2. Verificar que todo funciona
./mvnw quarkus:dev &
sleep 5
./scripts/test-api.sh

# 3. ¡Empezar a desarrollar!
```

### 📅 Desarrollo Diario
```bash
# 1. Activar entorno Java (si usas múltiples versiones)
source ./scripts/java-env.sh
java_env_setup

# 2. Iniciar desarrollo
./mvnw quarkus:dev

# 3. Hacer cambios, los hooks verificarán automáticamente al hacer commit
git add .
git commit -m "feat: nueva funcionalidad"  # ← hooks se ejecutan aquí

# 4. Antes de push, verificar API completa
./scripts/test-api.sh
```

### 🔄 Mantenimiento Periódico
```bash
# Verificar estado de herramientas
./scripts/setup-dev.sh --check

# Actualizar hooks si hay cambios
./scripts/git-hooks.sh reinstall

# Tests completos de regresión
./mvnw clean test
./scripts/test-api.sh --verbose
```

---

## ❓ Solución de Problemas Comunes

### 🐛 Script setup-dev.sh falla

**❌ "SDKMAN not found"**
```bash
# Instalar SDKMAN manualmente
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
./scripts/setup-dev.sh
```

**❌ "Java 21 not available"**
```bash
# Instalar Java 21 manualmente
sdk install java 21.0.4-tem
sdk use java 21.0.4-tem
```

### 🧪 Script test-api.sh falla

**❌ "Connection refused"**
```bash
# Verificar que la app está corriendo
curl http://localhost:8081/q/health

# Si no responde, iniciar aplicación
./mvnw quarkus:dev
```

**❌ Tests individuales fallan**
```bash
# Ejecutar en modo debug
./scripts/test-api.sh --verbose --debug

# Ver logs de la aplicación
./mvnw quarkus:dev
# Revisar output de la aplicación
```

### 🔗 Hooks de Git no funcionan

**❌ Hook no se ejecuta**
```bash
# Verificar permisos
ls -la .git/hooks/pre-commit
# Debería tener permisos +x

# Reinstalar hooks
./scripts/git-hooks.sh reinstall
```

**❌ "mvnw command not found" en hook**
```bash
# El hook necesita el path completo
./scripts/git-hooks.sh reinstall
# Esto actualiza el path en los hooks
```

---

## 📚 Recursos Adicionales

### 🔧 Personalización de Scripts

Todos los scripts son **modificables** y están documentados internamente:

```bash
# Ver código y comentarios detallados
cat ./scripts/setup-dev.sh
cat ./scripts/test-api.sh
```

### 🎯 Integración con IDEs

Los scripts configuran automáticamente:
- **VS Code**: `.vscode/settings.json`
- **IntelliJ**: `.idea/`
- **Variables de entorno** para cualquier IDE

### 📊 Métricas y Monitoring

```bash
# Ver tiempo de ejecución de scripts
time ./scripts/test-api.sh

# Logs detallados para análisis
./scripts/setup-dev.sh 2>&1 | tee setup.log
```

---

## 🎉 ¡Usa los Scripts con Confianza!

Estos scripts están diseñados para ser **confiables, rápidos y fáciles de usar**. Si encuentras algún problema o tienes sugerencias, no dudes en:

1. **Ver el código** - Todo está documentado
2. **Modificar según necesites** - Son completamente personalizables
3. **Reportar problemas** - Al equipo de desarrollo

**¡Feliz automatización!** 🚀

---

*📝 Documentación actualizada: Septiembre 2025*  
*🔄 Scripts compatibles con: macOS, Linux, Windows (WSL)*

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
