# 🏦 Servicio de Transacciones - Plantilla Quarkus

[![Quarkus](https://img.shields.io/badge/Quarkus-3.15.1-blue.svg)](https://quarkus.io/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-green.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Una plantilla de microservicio lista para producción construida con **Quarkus** y **Java 21**, implementando **Arquitectura Hexagonal** para el manejo de transacciones financieras.

## 📋 Tabla de Contenidos

- [🚀 Características](#-características)
- [🏗️ Arquitectura](#️-arquitectura)
- [⚡ Inicio Rápido](#-inicio-rápido)
- [🛠️ Configuración del Entorno](#️-configuración-del-entorno)
- [📊 API Endpoints](#-api-endpoints)
- [🧪 Pruebas](#-pruebas)
- [📝 Scripts Disponibles](#-scripts-disponibles)
- [🔧 Git Hooks](#-git-hooks)
- [📚 Documentación](#-documentación)
- [🐳 Docker](#-docker)
- [🔍 Monitoreo](#-monitoreo)

## 🚀 Características

### 🏗️ Arquitectura y Diseño
- **Arquitectura DDD por Entidades** (Domain-Driven Design)
- **Clean Architecture** con separación por bounded contexts
- **Organización por entidades de negocio** vs capas técnicas tradicionales
- **Alta cohesión y bajo acoplamiento** entre dominios
- **Preparación para microservicios** con contexts bien definidos
- **Inyección de dependencias** con CDI
- **Patrón Repository** para acceso a datos
- **Manejo centralizado de errores** por dominio

### 🔧 Stack Tecnológico
- **Quarkus 3.15.1** - Framework reactivo supersónico
- **Java 21** - Última versión LTS con características modernas
- **JAX-RS** - API REST con soporte JSON (Jackson)
- **Bean Validation** - Validación comprehensiva de datos
- **OpenAPI/Swagger** - Documentación automática de API
- **Health Checks & Metrics** - Monitoreo y observabilidad

### 📊 Calidad de Código
- **Spotless** - Formateo consistente de código
- **Checkstyle** - Estilo de código (Google Style Guide)
- **Maven Enforcer** - Gestión de dependencias
- **JUnit 5 + Mockito + AssertJ** - Suite completa de pruebas

### 🎯 Funcionalidades del Negocio
- **Gestión de transacciones** entre cuentas
- **Validación de números de cuenta**
- **Soporte multicurrency**
- **API RESTful** con versionado (`/api/v1`)
- **Repositorio en memoria** (fácil migración a base de datos)

## 🏗️ Arquitectura

### Arquitectura DDD por Entidades (Actualizada 2025)

```
src/main/java/com/example/transactions/
├── 📁 application/                    # Capa de Aplicación (por entidades)
│   ├── transaction/                   # Dominio Transaction
│   │   ├── dto/                      # TransactionQuery, TransactionDto, Commands
│   │   ├── usecases/                 # CreateTransaction, GetTransaction, ListTransactions
│   │   ├── mappers/                  # TransactionApplicationMapper
│   │   └── policies/                 # TransactionValidationPolicy
│   └── shared/                       # Componentes compartidos de aplicación
│       ├── pagination/               # PageRequest, PageResponse
│       └── exceptions/               # ValidationException
├── 📁 domain/                        # Capa de Dominio (por entidades)
│   ├── transaction/                  # Dominio core de Transaction
│   │   ├── model/                   # Transaction, TransactionStatus
│   │   ├── ports/                   # TransactionRepositoryPort
│   │   └── exceptions/              # TransactionNotFoundException
│   └── shared/                      # Dominio compartido
│       ├── exceptions/              # DomainException (base)
│       └── ports/                   # IdGeneratorPort
├── 📁 infrastructure/               # Capa de Infraestructura
│   ├── repositories/               # Adaptadores de persistencia
│   └── services/                   # Servicios de infraestructura
├── 📁 presentation/                # Capa de Presentación
│   ├── rest/                      # Controllers REST
│   └── dto/                       # DTOs de presentación
└── 📁 config/                     # Configuración transversal
    └── GlobalExceptionMapper.java  # Manejo global de excepciones
```

### ✨ Beneficios de la Arquitectura

#### 🎯 **Organización por Entidades de Negocio**
- **Mayor cohesión**: Todo lo relacionado con `Transaction` está junto
- **Localización de cambios**: Modificaciones en Transaction se hacen en su propio espacio  
- **Escalabilidad**: Fácil agregar nuevas entidades (`Account`, `Customer`, `Payment`)

#### 🚀 **Preparación para Microservicios**
- **Bounded contexts claros**: Cada entidad es un contexto delimitado
- **Extracción sencilla**: `domain/transaction/` + `application/transaction/` = microservicio independiente
- **Shared kernel**: `domain/shared/` y `application/shared/` para código común

### Principios Arquitectónicos
- **Entity-First Organization**: Organización por entidades de negocio vs capas técnicas
- **High Cohesion**: Código relacionado vive junto
- **Low Coupling**: Entidades independientes entre sí
- **Inversión de Dependencias**: Las capas externas dependen de las internas
- **Separación de Responsabilidades**: Cada capa y entidad tiene responsabilidad clara
- **Testabilidad**: Arquitectura que facilita pruebas por entidad

## ⚡ Inicio Rápido

### Prerrequisitos
- **Java 21+** (recomendado: Temurin u OpenJDK)
- **Maven 3.9+** (incluido Maven Wrapper)
- **Git** para control de versiones

### 🌐 Configuración de Puertos (CENTRALIZADA)

El proyecto utiliza una configuración centralizada de puertos que se puede controlar mediante variables de entorno:

```bash
# Puerto por defecto (todos los perfiles): 8080
# Cambiar puerto para desarrollo:
export DEV_PORT=8080
./mvnw quarkus:dev

# Cambiar puerto para producción:
export PORT=9090
java -jar target/quarkus-app/quarkus-run.jar

# Cambiar puerto para tests (normalmente automático = 0):
export TEST_PORT=8082
./mvnw test
```

### Configuración por Perfil
- **Desarrollo** (`%dev`): Puerto `${DEV_PORT:8080}` (default: 8080)
- **Producción** (`%prod`): Puerto `${PORT:8080}` (default: 8080)
- **Test** (`%test`): Puerto `${TEST_PORT:0}` (default: random)

### Instalación y Ejecución

```bash
# 1. Clonar el repositorio
git clone https://github.com/joacocoba/quarkus-starter-template.git
cd quarkus-starter-template

# 2. Configurar entorno (instala Java 21 si es necesario)
./scripts/setup/setup-dev.sh

# 3. Ejecutar en modo desarrollo
./mvnw quarkus:dev
```

### Verificación de la Instalación

```bash
# Verificar que el servicio está funcionando
curl http://localhost:8080/q/health/ready

# Probar el API
curl -X POST http://localhost:8080/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{"amount": 100.50, "currency": "USD", "originAccountNumber": "ACC-12345", "destinationAccountNumber": "ACC-67890"}'
```

## 🛠️ Configuración del Entorno

### Usando SDKMAN (Recomendado)

```bash
# Instalar SDKMAN
curl -s "https://get.sdkman.io" | bash
source ~/.sdkman/bin/sdkman-init.sh

# Instalar Java 21
sdk install java 21.0.4-tem
sdk use java 21.0.4-tem

# Verificar instalación
java -version
```

### Configuración Manual

1. **Descargar Java 21** desde [Adoptium](https://adoptium.net/)
2. **Configurar JAVA_HOME** en tu sistema
3. **Verificar Maven** viene incluido (Maven Wrapper)

### Variables de Entorno

```bash
export JAVA_HOME=/path/to/java21
export MAVEN_OPTS="-Xmx1024m -XX:MaxPermSize=256m"
```

## 📊 API Endpoints

### Transacciones

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/api/v1/transactions` | Crear nueva transacción |
| `GET` | `/api/v1/transactions/{id}` | Obtener transacción por ID |
| `GET` | `/api/v1/transactions` | Listar todas las transacciones |

### Monitoreo y Salud

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/q/health/ready` | Verificar si el servicio está listo |
| `GET` | `/q/health/live` | Verificar si el servicio está vivo |
| `GET` | `/q/metrics` | Métricas de la aplicación |
| `GET` | `/q/swagger-ui` | Documentación interactiva de la API |

### Ejemplo de Uso

```bash
# Crear transacción
curl -X POST http://localhost:8080/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 250.75,
    "currency": "USD", 
    "originAccountNumber": "ACC-123456",
    "destinationAccountNumber": "ACC-789012"
  }'

# Respuesta esperada
{
  "id": "uuid-generated",
  "amount": 250.75,
  "currency": "USD",
  "originAccountNumber": "ACC-123456",
  "destinationAccountNumber": "ACC-789012",
  "status": "COMPLETED",
  "timestamp": "2025-09-24T10:30:00Z"
}
```

## 🧪 Pruebas

### Ejecutar Todas las Pruebas

```bash
# Pruebas unitarias
./mvnw test

# Pruebas de integración  
./mvnw verify

# Pruebas con reporte de cobertura
./mvnw test jacoco:report
```

### Tipos de Pruebas

- **Unitarias**: Pruebas de lógica de dominio y aplicación
- **Integración**: Pruebas de API endpoints
- **Contratos**: Validación de esquemas JSON
- **Arquitectura**: Verificación de principios arquitectónicos

### Ejecutar Pruebas Específicas

```bash
# Solo pruebas de dominio
./mvnw test -Dtest="*DomainTest"

# Solo pruebas de API
./mvnw test -Dtest="*RestTest"

# Prueba específica
./mvnw test -Dtest="TransactionServiceTest#shouldCreateTransaction"
```

## 📝 Scripts Disponibles

Todos los scripts están ubicados en el directorio `scripts/` y están completamente documentados:

### `setup-dev.sh`
```bash
./scripts/setup-dev.sh
```
- 🔧 **Configuración automática del entorno de desarrollo**
- ☕ **Detección e instalación de Java 21 via SDKMAN**
- 📦 **Verificación de dependencias**
- 🎯 **Compilación y validación inicial del proyecto**

### `test-api.sh`
```bash
./scripts/test-api.sh
```
- 🧪 **Pruebas automatizadas de la API**
- 🔍 **Verificación de endpoints principales**
- 📊 **Health checks y validación de respuestas**
- 🎯 **Casos de prueba de transacciones**

### `java-env.sh`
```bash
source ./scripts/java-env.sh
```
- 🌐 **Gestión del entorno Java**
- 📋 **Configuración automática desde .sdkmanrc**
- ✅ **Verificación de versiones**
- 🔧 **Activación de herramientas de desarrollo**

### `git-hooks.sh`
```bash
./scripts/git-hooks.sh [comando]
```
- 🎣 **Gestión completa de Git hooks**
- Ver sección [Git Hooks](#-git-hooks) para detalles

## 🔧 Git Hooks

El proyecto incluye un sistema completo de Git hooks para mantener la calidad del código:

### Comandos Disponibles

```bash
# Verificar estado del hook
./scripts/git-hooks.sh status

# Probar el hook manualmente  
./scripts/git-hooks.sh test

# Deshabilitar temporalmente
./scripts/git-hooks.sh disable

# Rehabilitar 
./scripts/git-hooks.sh enable

# Ver ayuda completa
./scripts/git-hooks.sh
```

### ¿Qué hace el Pre-commit Hook?

El hook de pre-commit ejecuta automáticamente antes de cada commit:

1. **✅ Verificación de Compilación**
   ```bash
   ./mvnw compile -q -DskipTests
   ```

2. **🧪 Ejecución de Pruebas**
   ```bash
   ./mvnw test -q
   ```

3. **🎨 Validación de Formato de Código**
   ```bash
   ./mvnw spotless:check -q
   ```

4. **🔍 Detección de Problemas Comunes**
   - TODOs sin resolver
   - Declaraciones de debug (System.out, printStackTrace)
   - Archivos grandes (>1MB)
   - Archivos binarios no deseados

### Configuración del Hook

El hook se instala automáticamente cuando ejecutas `setup-dev.sh`. Para instalarlo manualmente:

```bash
# El hook ya existe en .git/hooks/pre-commit
# Solo necesitas verificar que esté activo
./scripts/git-hooks.sh status
```

### Bypass Temporal (Solo en Emergencias)

```bash
# Commit omitiendo el hook (NO recomendado)
git commit --no-verify -m "Mensaje de commit"

# Mejor opción: deshabilitar temporalmente
./scripts/git-hooks.sh disable
git commit -m "Mensaje de commit"
./scripts/git-hooks.sh enable
```

## 📚 Documentación

### Documentación de la API
- **Swagger UI**: http://localhost:8080/q/swagger-ui/
- **OpenAPI JSON**: http://localhost:8080/q/openapi

### Estructura de Documentación

```
📚 Documentación
├── README.md              # Este archivo - Guía principal
├── scripts/README.md      # Documentación de scripts
├── docs/                 # Documentación adicional
│   └── architecture.md   # Decisiones arquitectónicas
└── API Documentation     # Swagger/OpenAPI automático
```

### Generar Documentación

```bash
# Generar documentación de API
./mvnw compile

# La documentación estará disponible en:
# http://localhost:8080/q/swagger-ui/
```

## 🐳 Docker

### Construcción de Imagen

```bash
# Construir la aplicación primero
./mvnw package

# Construir imagen Docker
docker build -t transactions-service .
```

### Ejecutar con Docker

```bash
# Ejecutar el contenedor
docker run -i --rm -p 8080:8080 transactions-service

# Verificar que funciona
curl http://localhost:8080/q/health/ready
```

### Variables de Entorno

```bash
# Ejecutar con variables personalizadas
docker run -i --rm -p 8080:8080 \
  -e QUARKUS_PROFILE=prod \
  -e PORT=8080 \
  transactions-service
```

## 🔍 Monitoreo

### Health Checks

```bash
# Verificar que el servicio está listo para recibir tráfico
curl http://localhost:8080/q/health/ready

# Verificar que el servicio está funcionando
curl http://localhost:8080/q/health/live
```

### Métricas

```bash
# Métricas de la aplicación (formato Prometheus)
curl http://localhost:8080/q/metrics

# Métricas específicas de la aplicación
curl http://localhost:8080/q/metrics/application
```

### Logs

```bash
# Logs en modo desarrollo (más verbosos)
./mvnw quarkus:dev

# Logs en producción (configurables via application.properties)
java -jar target/quarkus-app/quarkus-run.jar
```

## 🚀 Modo de Desarrollo

```bash
# Iniciar en modo desarrollo (hot reload)
./mvnw quarkus:dev

# El servicio estará disponible en:
# - API: http://localhost:8080
# - Swagger UI: http://localhost:8080/q/swagger-ui/
# - Health: http://localhost:8080/q/health
```

### Características del Modo Dev
- **Hot Reload**: Cambios automáticos sin reiniciar
- **Dev UI**: Interfaz de desarrollo en http://localhost:8080/q/dev/
- **Test Continuous**: Pruebas automáticas en background
- **Live Coding**: Desarrollo en tiempo real

## 🤝 Contribución

1. **Fork** el repositorio
2. **Crear rama** para tu feature (`git checkout -b feature/nueva-caracteristica`)
3. **Commit** tus cambios (`git commit -m 'feat: agregar nueva característica'`)
4. **Push** a la rama (`git push origin feature/nueva-caracteristica`)
5. **Crear Pull Request**

### Estándares de Código
- **Seguir Google Java Style Guide**
- **Usar Conventional Commits** para mensajes
- **Escribir pruebas** para nuevo código
- **Mantener cobertura** > 80%

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para detalles.

## 👥 Autores

- **BPD**

## 🙏 Agradecimientos

- **Quarkus Team** por el excelente framework
- **Red Hat** por el ecosistema de herramientas
- **Comunidad Java** por las mejores prácticas

---

⭐ **¡Si te gustó este proyecto, dale una estrella!** ⭐
