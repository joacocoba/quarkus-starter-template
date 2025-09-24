#!/bin/bash
# Quick development setup script
echo "🚀 Setting up Quarkus Starter Template development environment..."

# Enhanced Java version check with SDKMAN detection
echo "☕ Checking Java version..."

# First, check if SDKMAN is available and try to initialize it
if [[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]]; then
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    echo "🔧 SDKMAN detected, checking for Java 21..."
    
    # Check if Java 21 is installed in SDKMAN
    if sdk list java | grep -q "21.0.8-tem"; then
        echo "📦 Java 21 found in SDKMAN, activating it..."
        sdk use java 21.0.8-tem
        echo "✅ Activated Java 21 via SDKMAN"
    fi
fi

if command -v java >/dev/null 2>&1; then
    # Get major version number more reliably
    JAVA_VERSION=$(java -version 2>&1 | head -n1 | sed -n 's/.*"\([0-9]*\)\..*/\1/p')
    if [ -z "$JAVA_VERSION" ]; then
        # Try alternative method for newer Java versions
        JAVA_VERSION=$(java -version 2>&1 | head -n1 | sed -n 's/.* "\([0-9]*\)".*/\1/p')
    fi
    
    echo "✅ Java major version: $JAVA_VERSION"
    
    if [ "$JAVA_VERSION" -lt 21 ]; then
        echo "❌ ERROR: Java 21+ required, current: $JAVA_VERSION"
        echo ""
        
        # Check if SDKMAN is available and has Java 21 installed
        if command -v sdk >/dev/null 2>&1; then
            if sdk list java | grep -q "21.0.8-tem"; then
                echo "🎯 Java 21 is installed via SDKMAN but not active!"
                echo "   Activating Java 21 now..."
                sdk use java 21.0.8-tem
                echo "✅ Java 21 activated!"
                echo ""
                echo "🔄 Restarting setup with Java 21..."
                exec "$0" "$@"
            else
                echo "🔧 SDKMAN detected! Would you like to install Java 21 automatically? (y/n)"
                read -r response
                if [[ "$response" =~ ^[Yy]$ ]]; then
                    echo "📦 Installing Java 21 via SDKMAN..."
                    sdk install java 21.0.8-tem
                    sdk use java 21.0.8-tem
                    echo "✅ Java 21 installed and activated!"
                    echo ""
                    echo "🔄 Restarting setup with new Java version..."
                    exec "$0" "$@"
                fi
            fi
        else
            echo "💡 For automatic Java version management, install SDKMAN first:"
            echo "   curl -s \"https://get.sdkman.io\" | bash"
            echo "   source ~/.sdkman/bin/sdkman-init.sh"
            echo "   sdk install java 21.0.4-tem"
            echo ""
        fi
        
        echo "Manual activation options:"
        echo "• SDKMAN: sdk use java 21.0.8-tem"
        echo "• Set JAVA_HOME manually to Java 21 installation"
        echo "• Homebrew: brew install openjdk@21"
        echo "• Manual: https://adoptium.net/"
        echo ""
        echo "After activation, restart your terminal or run:"
        echo "export JAVA_HOME=\$(java -XshowSettings:properties 2>&1 | grep 'java.home' | awk '{print \$3}')"
        echo ""
        exit 1
    elif [ "$JAVA_VERSION" -eq 21 ]; then
        echo "✅ Perfect! Using Java 21 (recommended)"
    else
        echo "✅ Using Java $JAVA_VERSION (compatible)"
    fi
else
    echo "❌ Java not found. Please install Java 21+"
    exit 1
fi

# Verify Maven wrapper
echo "📦 Checking Maven wrapper..."
if [ -f "./mvnw" ]; then
    echo "✅ Maven wrapper found"
    # Test Maven with Java version check
    if ! ./mvnw --version >/dev/null 2>&1; then
        echo "⚠️  Maven wrapper test failed, but continuing..."
    fi
else
    echo "❌ Maven wrapper not found"
    exit 1
fi

# Format code to ensure consistency
echo "🎨 Applying consistent code formatting..."
if ! ./mvnw spotless:apply -q -DskipTests; then
    echo "⚠️  Code formatting had issues, but continuing..."
fi

# Run quick health check
echo "🧪 Running quick health check..."
echo "   • Compiling project..."
if ! ./mvnw compile -q -DskipTests; then
    echo "❌ Compilation failed"
    exit 1
fi

echo "   • Running tests..."
if ! ./mvnw test -q; then
    echo "❌ Tests failed"
    exit 1
fi

echo "✅ Project compiles and tests pass"

echo ""
echo "🎉 Setup completed successfully!"
echo ""
echo "Environment Information:"
echo "• Java Version: $JAVA_VERSION"
echo "• Java Home: ${JAVA_HOME:-$(java -XshowSettings:properties 2>&1 | grep 'java.home' | awk '{print $3}' | head -1)}"
echo "• Maven: $(./mvnw --version | head -1 | awk '{print $3}')"
echo ""
echo "Next steps:"
echo "• Run dev mode: ./mvnw quarkus:dev"
echo "• Test API: ./scripts/test-api.sh (after starting dev mode)"
echo "• View API docs: http://localhost:8081/q/swagger-ui"
echo ""
echo "IDE Integration:"
echo "• VSCode: Install recommended extensions from .vscode/extensions.json"
echo "• IntelliJ: Code style will be automatically applied"
echo ""
echo "Environment Files:"
echo "• Copy .env.example to .env for local environment variables"
echo "• Use 'dev' profile: export QUARKUS_PROFILE=dev"
