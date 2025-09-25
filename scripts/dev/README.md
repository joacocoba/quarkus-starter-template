# 🚀 Script `rd` - Run Development

**Shortcut rápido para iniciar Quarkus en modo desarrollo**

## 📋 ¿Qué hace?

Este script es un **alias súper corto** para `./mvnw quarkus:dev`, diseñado para desarrolladores que inician la aplicación muchas veces al día.

En lugar de escribir:
```bash
./mvnw quarkus:dev
```

Simplemente escribes:
```bash
./scripts/dev/rd
```

¡**5 caracteres en lugar de 18!** 🎯

## ⚡ Uso Rápido

```bash
# Desde la raíz del proyecto
./scripts/dev/rd
```

## 🔧 Características

- ✅ **Súper rápido de escribir** - Solo `rd`
- ✅ **Mismo comportamiento** que `./mvnw quarkus:dev`
- ✅ **Autocompletado** - Funciona con tab completion
- ✅ **Hot reload** - Recarga automática en desarrollo
- ✅ **Dev UI** - Acceso a http://localhost:8080/q/dev/
- ✅ **Tests automáticos** - Ejecuta tests en background

## 📚 Qué inicia

Cuando ejecutas `./scripts/dev/rd`, inicia:

1. **🌐 Servidor HTTP** en puerto 8080 (configurable con `DEV_PORT`)
2. **🔄 Live Reload** - Cambios automáticos al guardar archivos
3. **🧪 Tests en Background** - Ejecuta tests automáticamente
4. **📊 Dev UI** - Panel de desarrollo en `/q/dev/`
5. **📖 Swagger UI** - Documentación API en `/q/swagger-ui/`

## 🎯 Cuándo usarlo

### ✅ Perfecto para:
- **Desarrollo diario** - Inicio rápido del servidor
- **Debugging** - Modo desarrollo con hot reload
- **Testing rápido** - Ver cambios inmediatamente
- **Demo rápido** - Mostrar la aplicación funcionando

### ❌ NO usar para:
- **Producción** - Usar `./mvnw quarkus:run` o Docker
- **Tests de CI** - Usar `./mvnw test` directamente
- **Build final** - Usar `./mvnw package`

## 🌐 URLs Disponibles

Una vez que inicia, tienes acceso a:

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **API Principal** | http://localhost:8080/api/v1 | Endpoints de la aplicación |
| **Swagger UI** | http://localhost:8080/q/swagger-ui/ | Documentación interactiva |
| **Dev UI** | http://localhost:8080/q/dev/ | Panel de desarrollo Quarkus |
| **Health Check** | http://localhost:8080/q/health | Estado de la aplicación |
| **Métricas** | http://localhost:8080/q/metrics | Métricas Prometheus |

## ⚙️ Configuración

### 🎯 Puerto personalizado
```bash
# Cambiar puerto por defecto (8080 → 8090)
export DEV_PORT=8090
./scripts/dev/rd
```

### 🔧 JVM personalizada
```bash
# Más memoria para desarrollo
export MAVEN_OPTS="-Xmx2g -XX:MaxPermSize=512m"
./scripts/dev/rd
```

## 🛠️ Comandos interactivos

Una vez que `rd` está ejecutándose, puedes usar estos comandos interactivos:

| Tecla | Acción | Descripción |
|-------|--------|-------------|
| `r` | Resume testing | Reanudar tests automáticos |
| `o` | Toggle test output | Mostrar/ocultar salida de tests |
| `h` | Help | Mostrar ayuda de comandos |
| `s` | Force restart | Reiniciar aplicación |
| `q` | Quit | Salir de la aplicación |

## 🔥 Tips Pro

### 🎯 Alias en tu shell
Agrega esto a tu `.zshrc` o `.bashrc` para un acceso aún más rápido:

```bash
# En la raíz del proyecto
alias rd='./scripts/dev/rd'
alias dev='./scripts/dev/rd'
```

Ahora puedes ejecutar desde cualquier lugar:
```bash
rd        # ← ¡2 caracteres!
dev       # ← O este alias más descriptivo
```

### 🔄 Workflow de desarrollo típico
```bash
# 1. Iniciar desarrollo
rd

# 2. Hacer cambios en el código (en otro terminal/editor)
# Los cambios se reflejan automáticamente

# 3. Probar cambios
curl -X POST http://localhost:8080/api/v1/transactions \
  -H "Content-Type: application/json" \
  -d '{"amount": 100, "currency": "USD", "originAccountNumber": "ACC-123", "destinationAccountNumber": "ACC-456"}'

# 4. Ver documentación actualizada
open http://localhost:8080/q/swagger-ui/
```

## 🐛 Solución de problemas

### ❌ "Port already in use"
```bash
# Verificar qué está usando el puerto
lsof -i :8080

# Usar puerto diferente
DEV_PORT=8081 ./scripts/dev/rd
```

### ❌ "mvnw not found"
```bash
# Ejecutar desde la raíz del proyecto
cd /path/to/transactions-service
./scripts/dev/rd
```

### ❌ "Java version incorrect"
```bash
# Configurar Java 21
source ./scripts/setup/java-env.sh
java_env_setup

# Luego ejecutar rd
./scripts/dev/rd
```

## 💡 Comparación con otros comandos

| Comando | Propósito | Cuándo usar |
|---------|-----------|-------------|
| `./scripts/dev/rd` | **Desarrollo rápido** | Desarrollo diario |
| `./mvnw quarkus:dev` | Desarrollo completo | Cuando necesitas parámetros extra |
| `./mvnw quarkus:run` | **Producción local** | Testing de producción |
| `./mvnw test` | **Solo tests** | CI/CD o validación |
| `docker run ...` | **Contenedor** | Testing de Docker |

## 🎉 ¡Es así de simple!

```bash
./scripts/dev/rd
```

**¡Y listo! Tu aplicación está corriendo en modo desarrollo.** 🚀

---

*💡 Tip: Este script está optimizado para velocidad de desarrollo. Para builds de producción, usa los comandos Maven completos.*
