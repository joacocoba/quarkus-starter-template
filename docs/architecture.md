````markdown
# Arquitectura del Sistema

## Diseño del Sistema

El Servicio de Transacciones está construido usando **Arquitectura Hexagonal** (también conocida como Puertos y Adaptadores), asegurando una separación limpia de responsabilidades y facilidad de pruebas.

## Capas de Arquitectura

### 1. Capa de Dominio (`domain/`)
**Lógica de negocio pura, independiente del framework**

- **Modelos**: `Transaction`, `TransactionStatus`
- **Puertos**: `TransactionRepositoryPort`, `IdGeneratorPort`
- **Responsabilidad**: Encapsula las reglas de negocio y lógica del dominio

**Principios Clave**:
- Sin dependencias de frameworks
- Contiene todas las reglas de negocio
- Entidades inmutables cuando sea posible
- Modelo de dominio rico con comportamiento

### 2. Capa de Aplicación (`application/`)
**Casos de uso y orquestación**

- **Casos de Uso**: `CreateTransactionUseCase`, `GetTransactionUseCase`, `ListTransactionsUseCase`
- **DTOs**: `TransactionQuery`
- **Responsabilidad**: Orquesta objetos del dominio para cumplir casos de uso

**Principios Clave**:
- Capa de orquestación delgada
- Coordina entre dominio e infraestructura
- Operaciones sin estado
- Límites de transacción

### 3. Capa de Infraestructura (`infrastructure/`)
**Implementaciones técnicas**

- **Repositorios**: `InMemoryTransactionRepositoryAdapter`, `OracleTransactionRepositoryAdapter`
- **Servicios**: `UuidGeneratorAdapter`
- **Responsabilidad**: Implementa puertos con tecnología específica

**Principios Clave**:
- Implementa puertos del dominio
- Contiene todo el código específico de frameworks
- Implementaciones configurables
- Aislamiento tecnológico

### 4. Capa de Presentación (`presentation/`)
**Controladores de API y DTOs**

- **Recursos REST**: `TransactionResource`
- **DTOs**: Objetos de solicitud/respuesta
- **Manejo de Excepciones**: `GlobalExceptionMapper`
- **Responsabilidad**: Expone casos de uso vía API REST

**Principios Clave**:
- Controladores delgados
- Validación de entrada
- Semántica HTTP apropiada
- Preparado para versionado de API

### 5. Capa Compartida (`shared/`)
**Utilidades comunes y tipos**

- **Errores**: Excepciones del dominio
- **Tipos de Resultado**: Manejo funcional de resultados
- **Responsabilidad**: Componentes reutilizables entre capas

## Flujo de Datos

```
Petición HTTP → Presentación → Aplicación → Dominio ← Infraestructura
     ↓               ↓            ↓          ↓         ↓
  API REST → Casos de Uso → Lógica Dominio → Puertos ← Adaptadores
```

1. **Entrada**: HTTP → Controlador → Caso de Uso → Dominio
2. **Salida**: Dominio → Puerto → Adaptador de Infraestructura

## Stack Tecnológico

### Framework Principal
- **Quarkus 3.15.1**: Framework Java nativo para Kubernetes
- **Java 21**: Última versión LTS con características modernas del lenguaje
- **Maven**: Automatización de construcción y gestión de dependencias

### Calidad y Estándares
- **Spotless**: Formateo de código (Google Java Format)
- **Checkstyle**: Análisis estático de código
- **Maven Enforcer**: Cumplimiento de dependencias y versiones
- **EditorConfig**: Estilos de codificación consistentes

### Pruebas
- **JUnit 5**: Framework de pruebas unitarias
- **REST Assured**: Pruebas de API
- **AssertJ**: Aserciones fluidas
- **Mockito**: Framework de mocking

### Observabilidad
- **Micrometer**: Recolección de métricas
- **SmallRye Health**: Verificaciones de salud
- **Structured Logging**: Soporte para logging JSON

### Documentación de API
- **OpenAPI 3**: Especificación de API
- **Swagger UI**: Documentación interactiva

## Patrones de Diseño

### 1. Arquitectura Hexagonal
- **Puertos**: Definen contratos (interfaces)
- **Adaptadores**: Implementan contratos con tecnologías específicas
- **Dominio**: Contiene la lógica de negocio central

### 2. Inyección de Dependencias
- **Inyección por Constructor**: Preferida para dependencias requeridas
- **CDI**: Context and Dependency Injection para conectar componentes

### 3. Patrón Repository
- **Acceso Abstracto a Datos**: El dominio no conoce detalles de persistencia
- **Múltiples Implementaciones**: En memoria, Oracle, etc.

### 4. Patrón Caso de Uso
- **Responsabilidad Única**: Cada caso de uso maneja una operación de negocio
- **Orquestación**: Coordina objetos de dominio e infraestructura

### 5. Patrón DTO
- **Transferencia de Datos**: DTOs separados para API y objetos internos
- **Validación**: Validación de entrada en los límites

## Gestión de Configuración

### Perfiles
- **dev**: Desarrollo con almacenamiento en memoria
- **test**: Configuración de pruebas
- **prod**: Producción con base de datos Oracle

### Fuentes de Propiedades
- **application.properties**: Configuración por defecto
- **application-{profile}.properties**: Sobrescrituras específicas por perfil
- **Variables de Entorno**: Configuración en tiempo de ejecución

## Consideraciones de Seguridad

### Implementación Actual
- **Validación de Entrada**: Jakarta Validation en todas las entradas
- **Manejo de Errores**: Sin información sensible en respuestas de error
- **Ejecución Sin Root**: Contenedores Docker ejecutan como usuario sin root

### Mejoras Futuras
- Autenticación JWT
- Autorización basada en roles
- Limitación de velocidad
- Logging de solicitudes/respuestas

## Escalabilidad y Rendimiento

### Características Actuales
- **Diseño Sin Estado**: Sin estado de sesión en el servidor
- **Pool de Conexiones**: Gestión de conexiones a base de datos
- **Paginación**: Previene conjuntos de resultados grandes

### Consideraciones Futuras
- Capa de caché (Redis)
- Arquitectura dirigida por eventos
- Réplicas de lectura para consultas
- Patrón circuit breaker

## Monitoreo y Observabilidad

### Verificaciones de Salud
- **Preparación**: `/q/health/ready` - Listo para servir tráfico
- **Vitalidad**: `/q/health/live` - Aplicación en ejecución

### Métricas
- **Micrometer**: Métricas de JVM y personalizadas
- **Prometheus**: Endpoint de recolección de métricas

### Logging
- **Logs Estructurados**: Formato JSON para agregación de logs
- **IDs de Correlación**: Rastreo de solicitudes
- **Niveles de Log**: Configurables por paquete

## Estrategia de Pruebas

### Pruebas Unitarias
- **Capa de Dominio**: Pruebas unitarias puras, sin mocks
- **Capa de Aplicación**: Prueba casos de uso con puertos simulados
- **Capa de Infraestructura**: Prueba adaptadores con implementaciones reales

### Pruebas de Integración
- **Pruebas de API**: Ciclo completo de solicitud/respuesta HTTP
- **Pruebas de Repositorio**: Integración con base de datos
- **Pruebas de Contenedor**: Pruebas de imagen Docker

### Entornos de Prueba
- **En Memoria**: Pruebas rápidas con implementaciones en memoria
- **Testcontainers**: Pruebas de integración con bases de datos reales
- **CI/CD**: Pipeline automatizado de pruebas

## Ruta de Evolución

La arquitectura soporta evolución incremental:

1. **Fase 1**: Operaciones CRUD básicas (actual)
2. **Fase 2**: Integración con base de datos Oracle
3. **Fase 3**: Características dirigidas por eventos (patrón outbox)
4. **Fase 4**: Características avanzadas (caché, seguridad)
5. **Fase 5**: Patrones de microservicios (service mesh, rastreo distribuido)

````
