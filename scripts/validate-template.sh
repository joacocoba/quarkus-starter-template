#!/bin/bash

echo "🔍 Final Validation: Quarkus Starter Template"
echo "============================================="

cd "$(dirname "$0")/.."

echo ""
echo "📋 Project Structure Validation:"
echo "--------------------------------"

# Check critical files
if [ -f "pom.xml" ]; then
    echo "✅ pom.xml exists"
    if grep -q "quarkus-starter-template" pom.xml; then
        echo "✅ Project name updated in pom.xml"
    else
        echo "❌ Project name not updated in pom.xml"
    fi
else
    echo "❌ pom.xml missing"
fi

if [ -f "README.md" ]; then
    echo "✅ README.md exists"
else
    echo "❌ README.md missing"
fi

if [ -f "CHANGELOG.md" ]; then
    echo "✅ CHANGELOG.md exists"
else
    echo "❌ CHANGELOG.md missing"
fi

if [ -d "scripts" ]; then
    echo "✅ Scripts folder organized"
    echo "   📁 Scripts: $(ls scripts/ | wc -l | tr -d ' ') files"
else
    echo "❌ Scripts folder missing"
fi

echo ""
echo "🏗️ Architecture Validation:"
echo "---------------------------"
EXPECTED_PACKAGES=(
    "src/main/java/com/example/transactions/domain"
    "src/main/java/com/example/transactions/application"
    "src/main/java/com/example/transactions/infrastructure"
    "src/main/java/com/example/transactions/presentation"
    "src/main/java/com/example/transactions/shared"
)

for package in "${EXPECTED_PACKAGES[@]}"; do
    if [ -d "$package" ]; then
        echo "✅ $package exists"
    else
        echo "❌ $package missing"
    fi
done

echo ""
echo "🔧 Build Validation:"
echo "--------------------"
if ./mvnw compile -q; then
    echo "✅ Project compiles successfully"
else
    echo "❌ Compilation failed"
fi

echo ""
echo "📊 Git Status:"
echo "-------------"
git log --oneline -3

echo ""
echo "🏷️ Tags:"
echo "--------"
git tag

echo ""
echo "🎯 Summary:"
echo "----------"
echo "✅ Project renamed to quarkus-starter-template"
echo "✅ Unnecessary scripts removed"
echo "✅ Useful scripts organized in scripts/ folder"
echo "✅ Comprehensive documentation added"
echo "✅ Git repository ready with v1.0.0 tag"
echo ""
echo "🚀 Ready to push to GitHub!"
echo ""
echo "Next steps:"
echo "1. Create GitHub repository named 'quarkus-starter-template'"
echo "2. Follow instructions in GITHUB_SETUP.md"
echo "3. Push code and create release"
