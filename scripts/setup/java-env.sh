#!/bin/bash
# Java Environment Helper for this project
# Usage: source scripts/java-env.sh

# Check if SDKMAN is available
if [[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]]; then
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    
    # If .sdkmanrc exists, use it
    if [[ -f ".sdkmanrc" ]]; then
        echo "🔧 Setting up Java environment from .sdkmanrc..."
        sdk env
        echo "✅ Java environment configured!"
        echo "   Java Version: $(java -version 2>&1 | head -1)"
        echo "   Java Home: $JAVA_HOME"
    else
        echo "⚠️  .sdkmanrc not found, using default Java"
    fi
else
    echo "💡 SDKMAN not found. Install it for better Java version management:"
    echo "   curl -s \"https://get.sdkman.io\" | bash"
fi
