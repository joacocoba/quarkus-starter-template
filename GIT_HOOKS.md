# 🔧 Configuración de Git Hooks

Este proyecto incluye un hook de pre-commit que asegura la calidad del código antes de los commits.

## Qué hace el Hook de Pre-commit

El hook ejecuta automáticamente estas verificaciones antes de cada commit:

1. **📦 Verificación de Compilación**: Asegura que el código compile (`./mvnw compile`)
2. **🧪 Ejecución de Pruebas**: Ejecuta todas las pruebas (`./mvnw test`)
3. **🎨 Formato de Código**: Verifica el estilo del código (`./mvnw spotless:check`)
4. **📄 Calidad de Código**: Detecta problemas comunes:
   - Comentarios TODO/FIXME/XXX en archivos staged
   - Declaraciones de debug (`System.out.println`, `printStackTrace`)
   - Archivos grandes (>1MB)

## Cómo Funciona

El hook está **automáticamente activo** y:
- ✅ **Permite el commit** si todas las verificaciones pasan
- ❌ **Bloquea el commit** si se encuentran problemas críticos (compilación/pruebas/formato)
- ⚠️ **Muestra advertencias** por problemas de calidad pero permite el commit

## Gestión del Hook

Usa el script de gestión para controlar el hook:

```bash
# Verificar estado del hook
./scripts/git-hooks.sh status

# Probar hook manualmente (sin hacer commit)
./scripts/git-hooks.sh test

# Deshabilitar hook temporalmente
./scripts/git-hooks.sh disable

# Rehabilitar hook
./scripts/git-hooks.sh enable
```

## Soluciones Rápidas

Si el hook de pre-commit falla:

### Problemas de Compilación
```bash
./mvnw compile
# Corrige cualquier error de compilación mostrado
```

### Fallas en las Pruebas
```bash
./mvnw test
# Corrige las pruebas que fallen
```

### Problemas de Formato de Código
```bash
# Auto-corregir formato
./mvnw spotless:apply

# O verificar qué necesita ser corregido
./mvnw spotless:check
```

## Override de Emergencia

En casos raros donde necesites saltarte el hook:
```bash
git commit --no-verify -m "commit de emergencia"
```

⚠️ **Usar con moderación** - ¡esto omite todas las verificaciones de calidad!

## Beneficios

- 🛡️ **Previene código roto** de entrar al repositorio
- 🚀 **Mantiene calidad consistente** del código en todo el equipo
- 📈 **Reduce fallas en CI/CD** al detectar problemas temprano
- 🎯 **Ciclo de retroalimentación rápido** - detecta problemas en segundos, no minutos

El hook está diseñado para ser **rápido y práctico** - la mayoría de verificaciones se completan en menos de 10 segundos para commits típicos.
