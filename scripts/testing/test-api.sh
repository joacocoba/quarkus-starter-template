#!/bin/bash

echo "🧪 Testing Transactions API"
echo "=========================="

BASE_URL="http://localhost:8081/api/v1"

echo "1. Testing Health Check..."
curl -s "$BASE_URL/../q/health/ready" | jq . 2>/dev/null || echo "Health check response: $(curl -s $BASE_URL/../q/health/ready)"

echo -e "\n2. Testing POST /api/v1/transactions..."
RESPONSE=$(curl -s -w "HTTP_STATUS:%{http_code}" -X POST "$BASE_URL/transactions" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json" \
  -d '{
    "amount": 100.50,
    "currency": "USD",
    "originAccountNumber": "ACC-12345678",
    "destinationAccountNumber": "ACC-87654321"
  }')

HTTP_STATUS=$(echo $RESPONSE | grep -o "HTTP_STATUS:[0-9]*" | cut -d: -f2)
BODY=$(echo $RESPONSE | sed 's/HTTP_STATUS:[0-9]*$//')

echo "Status: $HTTP_STATUS"
echo "Response: $BODY" | jq . 2>/dev/null || echo "Response: $BODY"

if [ "$HTTP_STATUS" = "201" ]; then
    TRANSACTION_ID=$(echo $BODY | jq -r '.id' 2>/dev/null)
    echo -e "\n✅ Transaction created successfully! ID: $TRANSACTION_ID"
    
    echo -e "\n3. Testing GET /api/v1/transactions/$TRANSACTION_ID..."
    GET_RESPONSE=$(curl -s -w "HTTP_STATUS:%{http_code}" "$BASE_URL/transactions/$TRANSACTION_ID")
    GET_STATUS=$(echo $GET_RESPONSE | grep -o "HTTP_STATUS:[0-9]*" | cut -d: -f2)
    GET_BODY=$(echo $GET_RESPONSE | sed 's/HTTP_STATUS:[0-9]*$//')
    
    echo "Status: $GET_STATUS"
    echo "Response: $GET_BODY" | jq . 2>/dev/null || echo "Response: $GET_BODY"
    
    echo -e "\n4. Testing GET /api/v1/transactions (list)..."
    LIST_RESPONSE=$(curl -s -w "HTTP_STATUS:%{http_code}" "$BASE_URL/transactions")
    LIST_STATUS=$(echo $LIST_RESPONSE | grep -o "HTTP_STATUS:[0-9]*" | cut -d: -f2)
    LIST_BODY=$(echo $LIST_RESPONSE | sed 's/HTTP_STATUS:[0-9]*$//')
    
    echo "Status: $LIST_STATUS"
    echo "Response: $LIST_BODY" | jq . 2>/dev/null || echo "Response: $LIST_BODY"
else
    echo -e "\n❌ Failed to create transaction"
fi

echo -e "\n📝 To test in Swagger UI, go to: http://localhost:8081/q/swagger-ui"
echo "📝 Use the correct endpoints:"
echo "   - POST http://localhost:8081/api/v1/transactions"
echo "   - GET  http://localhost:8081/api/v1/transactions/{id}"
echo "   - GET  http://localhost:8081/api/v1/transactions"
