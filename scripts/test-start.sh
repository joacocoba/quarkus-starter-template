#!/bin/bash

echo "Testing Quarkus application startup..."

# Build the application
echo "1. Building application..."
./mvnw clean compile -q

if [ $? -eq 0 ]; then
    echo "✅ Build successful"
else
    echo "❌ Build failed"
    exit 1
fi

# Run tests
echo "2. Running tests..."
./mvnw test -q

if [ $? -eq 0 ]; then
    echo "✅ Tests passed"
else
    echo "❌ Tests failed"
    exit 1
fi

# Try to start dev mode (with timeout)
echo "3. Testing dev mode startup..."
timeout 30 ./mvnw quarkus:dev &
QUARKUS_PID=$!

# Wait a bit for startup
sleep 10

# Check if the process is still running
if kill -0 $QUARKUS_PID 2>/dev/null; then
    echo "✅ Quarkus dev mode started successfully"
    
    # Try to access the health endpoint
    if curl -s http://localhost:8081/q/health/ready >/dev/null; then
        echo "✅ Health check passed"
    else
        echo "⚠️  Health check failed (but app may still be starting)"
    fi
    
    # Stop the process
    kill $QUARKUS_PID 2>/dev/null
    wait $QUARKUS_PID 2>/dev/null
else
    echo "❌ Quarkus dev mode failed to start"
fi

echo "Test completed!"
