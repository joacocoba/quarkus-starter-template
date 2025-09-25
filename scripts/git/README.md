# 🔧 Scripts de Git - Automatización de Control de Versiones

Scripts para automatizar hooks de Git y asegurar calidad de código.

## 📁 Scripts Disponibles

### 🎣 `git-hooks.sh` - Git Hooks para Calidad de Código

**Instala y configura hooks de Git para automatizar checks de calidad**

#### 🎯 ¿Qué hace?

Este script configura **hooks de Git automáticos** que se ejecutan en momentos clave:

- ✅ **Pre-commit**: Valida código antes del commit
- ✅ **Pre-push**: Ejecuta tests antes del push
- ✅ **Post-merge**: Actualiza dependencias después del merge
- ✅ **Commit-msg**: Valida formato de mensajes de commit

#### ⚡ Uso Rápido

```bash
# Instalar todos los hooks
./scripts/git/git-hooks.sh --install

# Desinstalar hooks
./scripts/git/git-hooks.sh --uninstall

# Ver hooks instalados
./scripts/git/git-hooks.sh --status
```

#### 📋 Qué verás durante la instalación

```bash
🎣 Configurando Git hooks para calidad de código...

✅ [PRE-COMMIT] Hook instalado
   • Formatea código con Spotless
   • Valida sintaxis Java
   • Detecta archivos grandes
   
✅ [PRE-PUSH] Hook instalado  
   • Ejecuta unit tests
   • Valida que build pasa
   • Previene push con fallos
   
✅ [COMMIT-MSG] Hook instalado
   • Valida formato Conventional Commits
   • Ejemplos: feat:, fix:, docs:
   
✅ [POST-MERGE] Hook instalado
   • Actualiza dependencias Maven
   • Limpia target/ si es necesario

🎉 ¡Git hooks configurados exitosamente!

📋 Próximos pasos:
1. Hacer commit: git add . && git commit -m "feat: nueva funcionalidad"
2. Ver validación automática en acción
3. Push con confianza: git push
```

#### 🔧 Hooks Configurados

### 🚦 Pre-commit Hook

**Se ejecuta ANTES de cada commit**

```bash
# Lo que hace automáticamente:
🔍 [VALIDACIÓN] Verificando archivos a commitear...
🎨 [SPOTLESS] Aplicando formato de código...
✅ [SYNTAX] Validando sintaxis Java...
📏 [SIZE] Verificando tamaño de archivos...
🧪 [BASIC] Ejecutando tests básicos...

# Si todo está bien:
✅ Pre-commit validation passed
💾 Commit realizado exitosamente

# Si hay problemas:
❌ Pre-commit validation failed
🛠️ Fix issues above before committing
```

**🎯 Qué previene:**
- Código mal formateado
- Errores de sintaxis
- Archivos demasiado grandes (>10MB)
- Tests básicos que fallan
- Commits con TODOs críticos

### 🚀 Pre-push Hook

**Se ejecuta ANTES de cada push**

```bash
# Lo que hace automáticamente:
🏗️  [BUILD] Verificando que el proyecto compila...
🧪 [TESTS] Ejecutando suite de tests...
🔍 [COVERAGE] Verificando cobertura mínima...
🎯 [SMOKE] Ejecutando smoke tests...

# Si todo está bien:
✅ Pre-push validation passed
🚀 Push realizado exitosamente

# Si hay problemas:
❌ Pre-push validation failed
🛠️ Fix issues above before pushing
```

**🎯 Qué previene:**
- Push de código que no compila
- Push con tests que fallan
- Código con cobertura insuficiente
- Breaking changes sin avisar

### 📝 Commit-msg Hook

**Valida FORMATO de mensajes de commit**

```bash
# ✅ Mensajes válidos (Conventional Commits):
feat: agregar endpoint de búsqueda
fix: corregir error 500 en transacciones  
docs: actualizar documentación de API
test: agregar tests para TransactionService
refactor: mejorar estructura de packages

# ❌ Mensajes inválidos:
- "cambios varios"
- "fix bug" 
- "WIP"
- "asdf"

# Formato requerido:
<tipo>: <descripción>

# Tipos permitidos:
• feat     - Nueva funcionalidad
• fix      - Corrección de bug
• docs     - Cambios en documentación
• style    - Cambios de formato (sin cambios de código)
• refactor - Refactoring (sin nuevas funcionalidades ni fixes)
• test     - Agregar o modificar tests
• chore    - Cambios en build, dependencias, etc.
```

### 🔄 Post-merge Hook

**Se ejecuta DESPUÉS de cada merge**

```bash
# Lo que hace automáticamente:
📦 [DEPS] Verificando cambios en pom.xml...
🔄 [UPDATE] Actualizando dependencias si es necesario...
🧹 [CLEAN] Limpiando archivos temporales...
🏗️  [BUILD] Re-compilando con nuevos cambios...

# Output típico:
✅ Dependencies updated
✅ Clean build completed
🎉 Ready to continue development
```

**🎯 Qué automatiza:**
- Actualización de dependencias después de merge
- Limpieza de archivos de build antiguos
- Re-compilación para detectar conflictos

#### 🛠️ Opciones Avanzadas

```bash
# Instalar hooks específicos
./scripts/git/git-hooks.sh --install --hook pre-commit
./scripts/git/git-hooks.sh --install --hook pre-push

# Configurar severidad de validaciones
./scripts/git/git-hooks.sh --install --strict    # Muy estricto
./scripts/git/git-hooks.sh --install --lenient   # Menos estricto

# Instalar para todo el equipo
./scripts/git/git-hooks.sh --install --global

# Backup de hooks existentes
./scripts/git/git-hooks.sh --backup

# Restaurar hooks desde backup
./scripts/git/git-hooks.sh --restore
```

#### 🎯 Configuración por Equipo

**Para proyectos nuevos:**
```bash
# En setup inicial del proyecto
git clone <repo>
cd transactions-service
./scripts/git/git-hooks.sh --install
```

**Para equipos existentes:**
```bash
# Documentar en README principal:
echo "## Setup del Proyecto" >> README.md
echo "./scripts/git/git-hooks.sh --install" >> README.md
```

**En CI/CD:**
```bash
# Validar que hooks están instalados
./scripts/git/git-hooks.sh --status --ci
```

#### 🔍 Troubleshooting

**❌ "Pre-commit hook failing"**
```bash
# Ver qué está fallando exactamente
git commit -m "test" --verbose

# Ejecutar validaciones manualmente
./mvnw spotless:apply
./mvnw compile

# Si persiste, saltar temporalmente:
git commit -m "fix: emergency fix" --no-verify
```

**❌ "Pre-push takes too long"**
```bash
# Configurar tests más rápidos para pre-push
./scripts/git/git-hooks.sh --install --fast-tests

# O saltar temporalmente:
git push --no-verify
```

**❌ "Commit message format error"**
```bash
# Ver mensaje de error específico
git commit -m "mi mensaje"

# Usar formato correcto:
git commit -m "feat: descripción clara del cambio"
```

**❌ "Hook not executing"**
```bash
# Verificar permisos
ls -la .git/hooks/
chmod +x .git/hooks/*

# Reinstalar hooks
./scripts/git/git-hooks.sh --uninstall
./scripts/git/git-hooks.sh --install
```

#### 📊 Integración con IDE

**VS Code:**
```json
// En settings.json del workspace
{
  "git.alwaysSignOff": false,
  "git.enableCommitSigning": true,
  "conventionalCommits.autoComplete": true
}
```

**IntelliJ:**
```bash
# Instalar plugin "Conventional Commit"
# Configurar en: Settings → Version Control → Git
# ✅ Enable commit message template
```

#### 🎯 Personalización de Hooks

**Configuración de severidad:**
```bash
# .githooks-config (creado automáticamente)
HOOK_SEVERITY=strict          # strict | normal | lenient
SKIP_TESTS_ON_WIP=true       # Saltar tests en commits WIP
MAX_FILE_SIZE=10MB           # Tamaño máximo de archivos
MIN_COVERAGE=80              # Cobertura mínima requerida
```

**Hooks personalizados:**
```bash
# Crear hook personalizado en .git/hooks/
# El script principal los incluirá automáticamente
```

## 🎯 Flujo de Trabajo con Hooks

### 🌅 Desarrollo diario

```bash
# 1. Desarrollar funcionalidad
vim src/main/java/...

# 2. Hacer commit (hooks se ejecutan automáticamente)
git add .
git commit -m "feat: nueva funcionalidad"
# → Pre-commit hook formatea código y valida

# 3. Push (hooks validan calidad)
git push
# → Pre-push hook ejecuta tests completos
```

### 🔄 Trabajo en equipo

```bash
# 1. Pull con cambios del equipo
git pull origin main
# → Post-merge hook actualiza dependencias

# 2. Resolver conflictos si los hay
git add .
git commit -m "fix: resolver conflictos de merge"
# → Hooks validan la resolución

# 3. Push con confianza
git push
# → Hooks garantizan que no rompes nada
```

### 🎛️ Casos especiales

```bash
# Commit de emergencia (saltar hooks)
git commit -m "fix: emergency hotfix" --no-verify

# Push urgente (saltar tests)
git push --no-verify

# Commit WIP (formato relajado)
git commit -m "wip: trabajo en progreso"
# Los hooks detectan WIP y son menos estrictos
```

## 💡 Tips Pro

1. **🎯 Instalar siempre**: Primer paso en cualquier proyecto nuevo
2. **📝 Mensajes claros**: Usar Conventional Commits desde el inicio
3. **⚡ Tests rápidos**: Hooks usan subset de tests para velocidad
4. **🔧 Personalizar**: Ajustar severidad según el equipo
5. **🚀 CI compatible**: Hooks preparan el código para CI/CD

---

*🎣 Los hooks de Git son tu red de seguridad. ¡Configurar una vez, beneficio para siempre!*
