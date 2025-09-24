#!/bin/bash

echo "🔍 Diagnosing Test Issues"
echo "========================"

cd /Users/coba/Desktop/BPD/LBTR/OldMicroservices/transactions-service

echo ""
echo "1. Testing domain layer (should be fast)..."
if ./mvnw test -Dtest=TransactionTest -q; then
    echo "✅ Domain tests pass"
else
    echo "❌ Domain tests fail"
    exit 1
fi

echo ""
echo "2. Checking application startup in test mode..."
echo "   Starting Quarkus test with minimal logging..."

# Try to run a quick compilation test
if ./mvnw compile test-compile -q; then
    echo "✅ Test compilation successful"
else
    echo "❌ Test compilation failed"
    exit 1
fi

echo ""
echo "3. Checking for CDI issues..."
if grep -r "OracleTransactionRepositoryAdapter" src/main/java/ | grep -v "//"; then
    echo "⚠️ Found active OracleTransactionRepositoryAdapter - this might cause CDI conflicts"
else
    echo "✅ OracleTransactionRepositoryAdapter is properly commented out"
fi

echo ""
echo "4. Checking test configuration..."
if [ -f "src/test/resources/application-test.properties" ]; then
    echo "✅ Test configuration exists"
    echo "   Content:"
    cat src/test/resources/application-test.properties | sed 's/^/   /'
else
    echo "❌ Test configuration missing"
fi

echo ""
echo "5. Attempting basic integration test with timeout..."
echo "   This will test if Quarkus can start properly..."

# Create a simple test script that will timeout if hanging
cat > test_startup.sh << 'EOF'
#!/bin/bash
echo "Starting Quarkus test..."
./mvnw test -Dtest=TransactionResourceTest#shouldReturn404ForNonExistentTransaction &
TEST_PID=$!
sleep 15
if kill -0 $TEST_PID 2>/dev/null; then
    echo "Test is taking too long, likely hanging during startup"
    kill $TEST_PID
    exit 1
else
    echo "Test completed normally"
    wait $TEST_PID
    exit $?
fi
EOF

chmod +x test_startup.sh
if ./test_startup.sh; then
    echo "✅ Integration test passed"
else
    echo "❌ Integration test failed or hung during startup"
    echo ""
    echo "💡 Possible issues:"
    echo "   • CDI configuration problem"
    echo "   • Port conflicts"
    echo "   • Missing dependencies"
    echo "   • Test environment issues"
fi

rm -f test_startup.sh

echo ""
echo "🔧 Quick fix suggestions:"
echo "------------------------"
echo "1. Try running tests with: ./mvnw test -Dquarkus.test.hang-detection-timeout=60s"
echo "2. Check if port 8081 is available: lsof -i :8081"
echo "3. Try running a single test method instead of the whole class"
