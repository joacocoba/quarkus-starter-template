# ⚙️ Scripts de Setup - Configuración Automática del Entorno

Scripts para configuración automática del entorno de desarrollo.

## 📁 Scripts Disponibles

### 🚀 `setup-dev.sh` - Configuración Completa del Entorno

**Configura automáticamente todo lo necesario para desarrollar**

#### 🎯 ¿Qué hace?

Este script es el **"one-click setup"** del proyecto. Configura automáticamente:

- ✅ **Java 21** via SDKMAN
- ✅ **Maven** con wrapper
- ✅ **Git hooks** para calidad de código
- ✅ **Variables de entorno**
- ✅ **Verificaciones de herramientas**

#### ⚡ Uso Rápido

```bash
# Desde la raíz del proyecto
./scripts/setup/setup-dev.sh
```

#### 📋 Qué verás durante la ejecución

```bash
🚀 Configurando entorno de desarrollo...

✅ [JAVA] Java 21.0.4 detectado y configurado
✅ [MAVEN] Maven 3.9.8 disponible  
✅ [GIT] Git configurado correctamente
✅ [HOOKS] Pre-commit hooks instalados

🎉 ¡Entorno configurado exitosamente!

📋 Próximos pasos:
1. Ejecutar: ./scripts/dev/rd
2. Abrir: http://localhost:8080/q/swagger-ui
3. Probar: ./scripts/testing/test-api.sh
```

#### 🔧 Características Principales

**🔍 Detección Inteligente:**
- Detecta si SDKMAN está instalado
- Verifica versión de Java actual
- Identifica herramientas disponibles

**🛠️ Instalación Automática:**
- Instala Java 21 si no existe
- Configura JAVA_HOME automáticamente
- Crea archivos de configuración necesarios

**⚡ Verificaciones:**
- Compila el proyecto para verificar setup
- Ejecuta tests básicos
- Valida configuración completa

#### 🎯 Cuándo usarlo

**✅ Perfecto para:**
- **Primer día** en el proyecto
- **Nueva máquina** de desarrollo
- **Cambio de versión** de Java
- **Setup de CI/CD**
- **Problemas de configuración**

**🔄 Ejecuciones adicionales:**
- Es **seguro ejecutar múltiples veces**
- Detecta configuración existente
- Solo cambia lo necesario

#### 🛠️ Opciones Avanzadas

```bash
# Ver logs detallados
./scripts/setup/setup-dev.sh --verbose

# Forzar reinstalación completa
./scripts/setup/setup-dev.sh --force

# Solo verificar configuración actual
./scripts/setup/setup-dev.sh --check
```

#### 📋 Casos de Uso Específicos

**🆕 Nuevo desarrollador:**
```bash
git clone <repo>
cd transactions-service
./scripts/setup/setup-dev.sh
# ¡Listo para desarrollar!
```

**🔄 Cambio de versión de Java:**
```bash
sdk install java 21.0.5-tem
./scripts/setup/setup-dev.sh
# Reconfigura todo automáticamente
```

**🐛 Problemas de configuración:**
```bash
./scripts/setup/setup-dev.sh --force
# Reinstala todo desde cero
```

#### 🔍 Troubleshooting

**❌ "SDKMAN not found"**
```bash
# Instalar SDKMAN manualmente
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
./scripts/setup/setup-dev.sh
```

**❌ "Java 21 installation failed"**
```bash
# Instalar Java manualmente
sdk list java
sdk install java 21.0.4-tem
sdk use java 21.0.4-tem
```

**❌ "Permission denied"**
```bash
# Asegurar permisos correctos
chmod +x ./scripts/setup/setup-dev.sh
```

---

### ☕ `java-env.sh` - Gestión Inteligente de Java

**Funciones para gestionar versiones de Java con SDKMAN**

#### 🎯 ¿Qué hace?

Proporciona funciones de shell para gestionar Java de forma inteligente, similar a NVM para Node.js.

#### ⚡ Uso Rápido

```bash
# Cargar funciones
source ./scripts/setup/java-env.sh

# Configurar Java 21 automáticamente
java_env_setup
```

#### 🔧 Funciones Disponibles

**📋 Verificar estado:**
```bash
java_env_status
# Output:
# ✅ SDKMAN: Disponible
# ✅ Java 21: Activo (21.0.4-tem)
# ✅ JAVA_HOME: /Users/.../21.0.4-tem
```

**⚙️ Configurar automáticamente:**
```bash
java_env_setup
# Activa Java 21 para este proyecto
```

**📋 Listar versiones:**
```bash
java_env_list_versions
# Muestra todas las versiones instaladas
```

**⬇️ Instalar Java 21:**
```bash
java_env_install_java21
# Instala Java 21 si no existe
```

#### 🎯 Cuándo usarlo

**✅ Perfecto para:**
- **Scripts de CI/CD** que necesitan Java 21
- **Desarrolladores** que usan múltiples versiones de Java
- **Automatización** de configuración
- **Verificación** de entorno

#### 📚 Integración con Scripts

```bash
#!/bin/bash
# En cualquier script que necesite Java 21
source ./scripts/setup/java-env.sh
java_env_setup

# Ahora Java 21 está garantizado
mvn clean test
```

#### 🔧 Configuración Permanente

```bash
# Agregar a tu .zshrc/.bashrc
echo "source $(pwd)/scripts/setup/java-env.sh" >> ~/.zshrc

# Ahora tienes las funciones disponibles siempre
java_env_status
java_env_setup
```

## 🎯 Flujo de Setup Recomendado

### 🌅 Primera vez en el proyecto

```bash
# 1. Ejecutar setup completo
./scripts/setup/setup-dev.sh

# 2. Verificar que todo funciona
./scripts/dev/rd &
sleep 5
./scripts/testing/test-api.sh

# 3. ¡Empezar a desarrollar!
```

### 🔄 Mantenimiento regular

```bash
# Verificar estado del entorno
./scripts/setup/setup-dev.sh --check

# Si hay problemas, reconfigurar
./scripts/setup/setup-dev.sh
```

### 🎛️ Uso avanzado con múltiples versiones

```bash
# Cargar funciones Java
source ./scripts/setup/java-env.sh

# Trabajar en este proyecto (Java 21)
java_env_setup

# Cambiar a otro proyecto (Java 17)
sdk use java 17.0.8-tem

# Volver a este proyecto
cd transactions-service
java_env_setup  # ← Automáticamente vuelve a Java 21
```

## 💡 Tips Pro

1. **📋 Verificación rápida**: `./scripts/setup/setup-dev.sh --check`
2. **🔧 Recarga de funciones**: `source ./scripts/setup/java-env.sh`
3. **🚀 Setup completo**: `./scripts/setup/setup-dev.sh && ./scripts/dev/rd`
4. **💻 IDE compatible**: Los scripts configuran automáticamente VS Code e IntelliJ

---

*🎯 Estos scripts están diseñados para ser ejecutados una vez y olvidarse. ¡Tu entorno estará siempre listo!*
