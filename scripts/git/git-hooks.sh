#!/bin/bash
# Git hooks management script

HOOKS_DIR=".git/hooks"
HOOK_NAME="pre-commit"

case "$1" in
    "install")
        if [ -f "$HOOKS_DIR/$HOOK_NAME" ]; then
            echo "✅ Pre-commit hook is already installed"
            echo "📋 Current checks:"
            echo "   • Compilation check"
            echo "   • Test execution" 
            echo "   • Code formatting (Spotless)"
            echo "   • Common code issues detection"
        else
            echo "❌ Pre-commit hook not found. Please run this script from project root."
        fi
        ;;
    "test")
        echo "🧪 Testing pre-commit hook..."
        if [ -f "$HOOKS_DIR/$HOOK_NAME" ]; then
            $HOOKS_DIR/$HOOK_NAME
        else
            echo "❌ Pre-commit hook not found"
        fi
        ;;
    "disable")
        if [ -f "$HOOKS_DIR/$HOOK_NAME" ]; then
            mv "$HOOKS_DIR/$HOOK_NAME" "$HOOKS_DIR/$HOOK_NAME.disabled"
            echo "🚫 Pre-commit hook disabled (renamed to .disabled)"
        else
            echo "❌ Pre-commit hook not found"
        fi
        ;;
    "enable")
        if [ -f "$HOOKS_DIR/$HOOK_NAME.disabled" ]; then
            mv "$HOOKS_DIR/$HOOK_NAME.disabled" "$HOOKS_DIR/$HOOK_NAME"
            chmod +x "$HOOKS_DIR/$HOOK_NAME"
            echo "✅ Pre-commit hook enabled"
        else
            echo "❌ Disabled pre-commit hook not found"
        fi
        ;;
    "status")
        if [ -f "$HOOKS_DIR/$HOOK_NAME" ]; then
            echo "✅ Pre-commit hook is active"
        elif [ -f "$HOOKS_DIR/$HOOK_NAME.disabled" ]; then
            echo "🚫 Pre-commit hook is disabled"
        else
            echo "❌ No pre-commit hook found"
        fi
        ;;
    *)
        echo "🔧 Git Hooks Manager for Quarkus Starter Template"
        echo ""
        echo "Usage: $0 {install|test|disable|enable|status}"
        echo ""
        echo "Commands:"
        echo "  install  - Check if pre-commit hook is installed"
        echo "  test     - Test the pre-commit hook manually"
        echo "  disable  - Temporarily disable the hook"
        echo "  enable   - Re-enable a disabled hook"
        echo "  status   - Check hook status"
        echo ""
        echo "The pre-commit hook runs:"
        echo "  ✓ Compilation check (./mvnw compile)"
        echo "  ✓ Test execution (./mvnw test)"  
        echo "  ✓ Code formatting (./mvnw spotless:check)"
        echo "  ✓ Common issues detection (TODOs, debug statements, large files)"
        ;;
esac
