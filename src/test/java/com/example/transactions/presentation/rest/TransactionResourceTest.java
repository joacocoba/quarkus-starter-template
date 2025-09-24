package com.example.transactions.presentation.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TransactionResourceTest {

    @Test
    void shouldCreateTransaction() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {
                        "amount": 100.50,
                        "currency": "USD"
                    }
                    """)
                .when()
                .post("/transactions")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("amount", equalTo(100.5f))
                .body("currency", equalTo("USD"))
                .body("status", equalTo("PENDING"))
                .body("createdAt", notNullValue());
    }

    @Test
    void shouldRejectInvalidTransactionData() {
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {
                        "amount": -10.0,
                        "currency": "INVALID"
                    }
                    """)
                .when()
                .post("/transactions")
                .then()
                .statusCode(400)
                .body("code", equalTo("VALIDATION_ERROR"))
                .body("violations", notNullValue());
    }

    @Test
    void shouldGetTransactionById() {
        // First create a transaction
        String transactionId = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                    {
                        "amount": 75.25,
                        "currency": "EUR"
                    }
                    """)
                .when()
                .post("/transactions")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // Then retrieve it
        given()
                .when()
                .get("/transactions/{id}", transactionId)
                .then()
                .statusCode(200)
                .body("id", equalTo(transactionId))
                .body("amount", equalTo(75.25f))
                .body("currency", equalTo("EUR"))
                .body("status", equalTo("PENDING"));
    }

    @Test
    void shouldReturn404ForNonExistentTransaction() {
        given()
                .when()
                .get("/transactions/non-existent-id")
                .then()
                .statusCode(404)
                .body("code", equalTo("TRANSACTION_NOT_FOUND"));
    }

    @Test
    void shouldListTransactions() {
        // Create some transactions first
        for (int i = 0; i < 3; i++) {
            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(String.format("""
                        {
                            "amount": %d.00,
                            "currency": "USD"
                        }
                        """, (i + 1) * 10))
                    .when()
                    .post("/transactions")
                    .then()
                    .statusCode(201);
        }

        // List transactions
        given()
                .when()
                .get("/transactions")
                .then()
                .statusCode(200)
                .body("transactions", hasSize(3))
                .body("total", equalTo(3))
                .body("offset", equalTo(0))
                .body("limit", equalTo(20))
                .body("hasNext", equalTo(false));
    }

    @Test
    void shouldSupportPagination() {
        // Create multiple transactions
        for (int i = 0; i < 5; i++) {
            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(String.format("""
                        {
                            "amount": %d.00,
                            "currency": "USD"
                        }
                        """, (i + 1) * 10))
                    .when()
                    .post("/transactions")
                    .then()
                    .statusCode(201);
        }

        // Test pagination
        given()
                .queryParam("offset", 0)
                .queryParam("limit", 2)
                .when()
                .get("/transactions")
                .then()
                .statusCode(200)
                .body("transactions", hasSize(2))
                .body("total", equalTo(5))
                .body("offset", equalTo(0))
                .body("limit", equalTo(2))
                .body("hasNext", equalTo(true));
    }
}
