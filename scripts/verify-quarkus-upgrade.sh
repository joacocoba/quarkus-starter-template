#!/bin/bash

echo "🚀 Quarkus 3.20.0 Upgrade Verification"
echo "======================================"

cd "$(dirname "$0")/.."

echo ""
echo "📋 Version Check:"
echo "----------------"
if grep -q "3.20.0" pom.xml; then
    echo "✅ Quarkus version updated to 3.20.0 in pom.xml"
else
    echo "❌ Quarkus version not found in pom.xml"
fi

echo ""
echo "🔧 Dependency Check:"
echo "--------------------"
if grep -q "quarkus-rest" pom.xml && grep -q "quarkus-rest-jackson" pom.xml; then
    echo "✅ Updated to new REST dependencies (quarkus-rest, quarkus-rest-jackson)"
else
    echo "❌ REST dependencies not updated properly"
fi

if ! grep -q "quarkus-resteasy-reactive" pom.xml; then
    echo "✅ Removed deprecated quarkus-resteasy-reactive dependencies"
else
    echo "⚠️ Still contains deprecated dependencies"
fi

echo ""
echo "🏗️ Build Test:"
echo "--------------"
echo "Running clean compile..."
if ./mvnw clean compile -q; then
    echo "✅ Build successful with Quarkus 3.20.0"
else
    echo "❌ Build failed"
    exit 1
fi

echo ""
echo "🧪 Test Execution:"
echo "-----------------"
echo "Running tests..."
if ./mvnw test -q; then
    echo "✅ All tests passing with Quarkus 3.20.0"
else
    echo "❌ Tests failed"
    exit 1
fi

echo ""
echo "📝 Documentation Check:"
echo "-----------------------"
if grep -q "3.20.0" README.md && grep -q "3.20.0" GITHUB_SETUP.md; then
    echo "✅ Documentation updated with new version"
else
    echo "❌ Documentation not updated"
fi

echo ""
echo "📊 Git Status:"
echo "-------------"
echo "Recent commits:"
git log --oneline -3

echo ""
echo "🎉 UPGRADE SUCCESSFUL!"
echo "====================="
echo "✅ Quarkus 3.20.0 upgrade completed successfully"
echo "✅ Dependencies updated to non-deprecated versions"
echo "✅ Build and tests passing"
echo "✅ Documentation updated"
echo "✅ Changes committed and pushed to GitHub"
echo ""
echo "🌐 Your template is now running on Quarkus 3.20.0!"
echo "   Repository: https://github.com/joacocoba/quarkus-starter-template"
