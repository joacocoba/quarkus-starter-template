# � Scripts de Automatización - Transactions Service

Scripts organizados modularmente para automatizar el desarrollo, testing y deployment del microservicio.

## � Estructura Modular

```
scripts/
├── README.md                 ← Este archivo (índice principal)
├── dev/                     ← Scripts de desarrollo
│   ├── rd                   ← Atajo ultra-rápido (./mvnw quarkus:dev)
│   └── README.md            ← Documentación detallada
├── setup/                   ← Scripts de configuración
│   ├── setup-dev.sh         ← Setup automático del entorno
│   ├── java-env.sh          ← Gestión inteligente de Java
│   └── README.md            ← Guía completa de setup
├── testing/                 ← Scripts de testing
│   ├── test-api.sh          ← Testing completo de la API
│   └── README.md            ← Guía de testing y casos de uso
└── git/                     ← Scripts de Git y hooks
    ├── git-hooks.sh         ← Hooks de calidad automáticos
    └── README.md            ← Configuración de hooks avanzada
```

## 🎯 Flujo de Trabajo Típico

```bash
# 1. Setup inicial (una sola vez)
./scripts/setup/setup-dev.sh

# 2. Desarrollo diario
./scripts/dev/rd  # ← ¡Solo 5 caracteres!

# 3. Testing frecuente  
./scripts/testing/test-api.sh

# 4. Commit con confianza (hooks automáticos)
git add . && git commit -m "feat: nueva funcionalidad"
```

## 📋 Scripts Principales

### 🔥 [`./scripts/dev/rd`](./dev/README.md) - Desarrollo Ultra-Rápido
```bash
./scripts/dev/rd
# Equivale a: ./mvnw quarkus:dev
# ¡El comando más usado, por eso es tan corto!
```

### 🛠️ [`./scripts/setup/setup-dev.sh`](./setup/README.md) - Setup Completo
```bash
./scripts/setup/setup-dev.sh
# Configura Java 21, Maven, Git hooks y variables de entorno
```

### 🧪 [`./scripts/testing/test-api.sh`](./testing/README.md) - Testing Completo
```bash
./scripts/testing/test-api.sh
# Unit tests + Integration tests + API tests + Smoke tests
```

### 🎣 [`./scripts/git/git-hooks.sh`](./git/README.md) - Git Hooks Automáticos
```bash
./scripts/git/git-hooks.sh --install
# Pre-commit, pre-push, commit-msg, post-merge hooks
```

## 🔍 Documentación Detallada

Cada categoría de scripts tiene su propia documentación completa:

- **[📖 Scripts de Desarrollo](./dev/README.md)** - Todo sobre el script `rd` y desarrollo diario
- **[📖 Scripts de Setup](./setup/README.md)** - Configuración automática del entorno y Java
- **[📖 Scripts de Testing](./testing/README.md)** - Testing completo con casos de uso y troubleshooting
- **[📖 Scripts de Git](./git/README.md)** - Hooks automáticos y control de calidad

## ⚡ Comandos Más Usados

```bash
# 🚀 Para empezar a desarrollar YA
./scripts/setup/setup-dev.sh && ./scripts/dev/rd

# 🧪 Para validar antes de commit
./scripts/testing/test-api.sh

# 🎯 Para ver el estado de todos los scripts
find scripts/ -name "README.md" -exec echo "📖 {}" \;
```

## 🌟 Ventajas de la Estructura Modular

✅ **Organización clara**: Cada script en su categoría lógica  
✅ **Documentación específica**: README detallado por categoría  
✅ **Fácil mantenimiento**: Cambios localizados por funcionalidad  
✅ **Escalabilidad**: Fácil agregar nuevos scripts por categoría  
✅ **Discoverability**: Estructura intuitiva para nuevos desarrolladores

## 💡 Tips Pro

1. **⚡ Ultra productivo**: `./scripts/dev/rd` es tu mejor amigo diario
2. **🎯 Setup perfecto**: `./scripts/setup/setup-dev.sh` una sola vez, funciona siempre
3. **🧪 Calidad garantizada**: `./scripts/testing/test-api.sh` antes de cada commit
4. **🎣 Automatización total**: `./scripts/git/git-hooks.sh --install` para workflows automáticos
5. **� Documentación rica**: Cada script tiene ejemplos detallados en su README

---

*🚀 Scripts modularizados = productividad maximizada. ¡Explora cada categoría para dominar tu workflow!*
