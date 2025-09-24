# 🌐 Configuración Centralizada de Puertos

Este documento explica cómo funciona la configuración centralizada de puertos en el servicio de transacciones.

## 📋 Tabla de Contenidos

- [🎯 Objetivo](#-objetivo)
- [🔧 Configuración](#-configuración)
- [🌍 Variables de Entorno](#-variables-de-entorno)
- [📝 Ejemplos de Uso](#-ejemplos-de-uso)
- [🔍 Verificación](#-verificación)

## 🎯 Objetivo

Centralizar toda la configuración de puertos en un solo lugar para:
- ✅ **Evitar duplicación** de configuración
- ✅ **Facilitar cambios** de puerto sin tocar múltiples archivos
- ✅ **Mejorar mantenimiento** del proyecto
- ✅ **Permitir configuración** por ambiente fácilmente

## 🔧 Configuración

### Archivo Principal: `application.properties`

```properties
# HTTP Configuration - Centralized port configuration
%prod.quarkus.http.port=${PORT:8080}
%dev.quarkus.http.port=${DEV_PORT:8080}
%test.quarkus.http.port=${TEST_PORT:0}
quarkus.http.host=localhost

# OpenAPI Configuration - Dynamic server URL based on profile
%prod.quarkus.smallrye-openapi.servers=${API_BASE_URL:http://localhost:${PORT:8080}}
%dev.quarkus.smallrye-openapi.servers=http://localhost:${DEV_PORT:8080}
%test.quarkus.smallrye-openapi.servers=http://localhost:${TEST_PORT:0}
```

### Archivo Desarrollo: `application-dev.properties`

```properties
# Enable CORS for development - Using dynamic port
quarkus.http.cors.origins=http://localhost:3000,http://localhost:${DEV_PORT:8080}

# OpenAPI Configuration for Development - Using dynamic port
# Server URL is now controlled from main application.properties
```

### Archivo Test: `application-test.properties`

```properties
# Use random ports for tests to avoid conflicts  
%test.quarkus.http.port=0
%test.quarkus.http.test-port=0
%test.quarkus.http.test-ssl-port=0
```

## 🌍 Variables de Entorno

### Variables Disponibles

| Variable | Perfil | Valor por Defecto | Descripción |
|----------|--------|-------------------|-------------|
| `PORT` | Producción | `8080` | Puerto principal para producción |
| `DEV_PORT` | Desarrollo | `8080` | Puerto para modo desarrollo |
| `TEST_PORT` | Test | `0` (random) | Puerto para pruebas |
| `API_BASE_URL` | Producción | `http://localhost:${PORT}` | URL base completa |

### Configuración por Perfil

#### 🚀 Producción (`%prod`)
```bash
export PORT=9090
java -jar target/quarkus-app/quarkus-run.jar
# Aplicación disponible en: http://localhost:9090
```

#### 🔧 Desarrollo (`%dev`)
```bash
export DEV_PORT=8080
./mvnw quarkus:dev
# Aplicación disponible en: http://localhost:8080
```

#### 🧪 Test (`%test`)
```bash
export TEST_PORT=8082  # Opcional, normalmente se usa puerto random (0)
./mvnw test
```

## 📝 Ejemplos de Uso

### Cambiar Puerto de Desarrollo

```bash
# Opción 1: Variable de entorno
export DEV_PORT=8080
./mvnw quarkus:dev

# Opción 2: Parámetro JVM
./mvnw quarkus:dev -DDEV_PORT=8080

# Opción 3: Parámetro Maven
./mvnw quarkus:dev -Dquarkus.http.port=8080
```

### Cambiar Puerto de Producción

```bash
# Opción 1: Variable de entorno
export PORT=9090
java -jar target/quarkus-app/quarkus-run.jar

# Opción 2: Parámetro JVM
java -DPORT=9090 -jar target/quarkus-app/quarkus-run.jar
```

### Docker con Puerto Personalizado

```bash
# Dockerfile
docker run -e PORT=9090 -p 9090:9090 transactions-service:latest

# Docker Compose
version: '3.8'
services:
  transactions-service:
    image: transactions-service:latest
    environment:
      - PORT=9090
    ports:
      - "9090:9090"
```

## 🔍 Verificación

### Verificar Puerto Activo

```bash
# Ver en qué puerto está corriendo
netstat -tlnp | grep java
lsof -i :8080
```

### Verificar Configuración Cargada

```bash
# Ver configuración actual de Quarkus
curl http://localhost:8080/q/info

# Health check con puerto dinámico
curl http://localhost:${DEV_PORT:-8080}/q/health/ready
```

### URLs de Verificación

Con la configuración centralizada, estas URLs se ajustan automáticamente:

```bash
# API Principal
http://localhost:${DEV_PORT:-8080}/api/v1/transactions

# Swagger UI (documentación)  
http://localhost:${DEV_PORT:-8080}/q/swagger-ui/

# Health Checks
http://localhost:${DEV_PORT:-8080}/q/health/ready
http://localhost:${DEV_PORT:-8080}/q/health/live

# Métricas
http://localhost:${DEV_PORT:-8080}/q/metrics
```

## 📚 Archivos Relacionados

### Archivos que Usan la Configuración Centralizada

- ✅ `src/main/resources/application.properties` - Configuración principal
- ✅ `src/main/resources/application-dev.properties` - Configuración desarrollo
- ✅ `src/test/resources/application-test.properties` - Configuración test
- ✅ `README.md` - Documentación actualizada
- ✅ Todos los ejemplos curl y URLs de documentación

### Archivos que NO Necesitan Cambio

- ✅ Código Java - No tiene referencias hardcodeadas a puertos
- ✅ Tests - Usan `@TestHTTPResource` que es dinámico
- ✅ Docker - Se configura via variables de entorno

## 🎯 Ventajas de Esta Configuración

1. **📍 Puerto Único**: Solo se define en un lugar
2. **🔧 Fácil Cambio**: Cambiar puerto sin editar múltiples archivos
3. **🌍 Por Ambiente**: Diferentes puertos para dev, prod, test
4. **🐳 Docker Ready**: Compatible con contenedores
5. **☁️ Cloud Ready**: Compatible con servicios en la nube
6. **🔄 Backward Compatible**: Mantiene compatibilidad con configuración anterior

## 🚨 Notas Importantes

- **Puerto 0**: En tests, puerto 0 significa "asignar puerto random disponible"
- **Orden de Precedencia**: Variable entorno > Parámetro JVM > Valor por defecto
- **CORS**: Se ajusta automáticamente al puerto configurado
- **OpenAPI**: Las URLs del swagger se actualizan automáticamente

---

¡Con esta configuración centralizada, cambiar el puerto es tan fácil como cambiar una variable de entorno! 🚀
