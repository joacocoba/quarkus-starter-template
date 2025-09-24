package com.example.transactions.presentation.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import jakarta.ws.rs.core.MediaType;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionResourceTest {

  @Test
  @Order(1)
  void shouldCreateTransaction() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            """
                    {
                        "amount": 100.50,
                        "currency": "USD",
                        "originAccountNumber": "ACC-123456789",
                        "destinationAccountNumber": "ACC-987654321"
                    }
                    """)
        .when()
        .post("/api/v1/transactions")
        .then()
        .statusCode(201)
        .header("Location", notNullValue())
        .body("id", notNullValue())
        .body("amount", equalTo(100.5f))
        .body("currency", equalTo("USD"))
        .body("originAccountNumber", equalTo("ACC-123456789"))
        .body("destinationAccountNumber", equalTo("ACC-987654321"))
        .body("status", equalTo("PENDING"))
        .body("createdAt", notNullValue());
  }

  @Test
  @Order(0)
  void shouldReturnHealthCheck() {
    given().when().get("/q/health/ready").then().statusCode(200);
  }

  @Test
  @Order(2)
  void shouldRejectInvalidTransactionData() {
    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            """
                    {
                        "amount": -10.0,
                        "currency": "INVALID",
                        "originAccountNumber": "SHORT",
                        "destinationAccountNumber": "ACC-987654321"
                    }
                    """)
        .when()
        .post("/api/v1/transactions")
        .then()
        .statusCode(400);
  }

  @Test
  @Order(3)
  void shouldGetTransactionById() {
    // First create a transaction
    String transactionId =
        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                """
                    {
                        "amount": 75.25,
                        "currency": "EUR",
                        "originAccountNumber": "ACC-111111111",
                        "destinationAccountNumber": "ACC-222222222"
                    }
                    """)
            .when()
            .post("/api/v1/transactions")
            .then()
            .statusCode(201)
            .extract()
            .path("id");

    // Then retrieve it
    given()
        .when()
        .get("/api/v1/transactions/{id}", transactionId)
        .then()
        .statusCode(200)
        .body("id", equalTo(transactionId))
        .body("amount", equalTo(75.25f))
        .body("currency", equalTo("EUR"))
        .body("originAccountNumber", equalTo("ACC-111111111"))
        .body("destinationAccountNumber", equalTo("ACC-222222222"))
        .body("status", equalTo("PENDING"));
  }

  @Test
  @Order(4)
  void shouldReturn404ForNonExistentTransaction() {
    given().when().get("/api/v1/transactions/non-existent-id").then().statusCode(404);
  }

  @Test
  @Order(5)
  void shouldListTransactions() {
    // Since we use shared in-memory repository, we'll have previous transactions
    // Get current transaction count first
    int currentCount =
        given().when().get("/api/v1/transactions").then().statusCode(200).extract().path("total");

    // Create some transactions first
    for (int i = 0; i < 3; i++) {
      given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(
              String.format(
                  """
                        {
                            "amount": %d.00,
                            "currency": "USD",
                            "originAccountNumber": "ACC-%09d",
                            "destinationAccountNumber": "ACC-%09d"
                        }
                        """,
                  (i + 1) * 10, 100000000 + i, 200000000 + i))
          .when()
          .post("/api/v1/transactions")
          .then()
          .statusCode(201);
    }

    // List transactions - expect current count + 3 new ones
    given()
        .when()
        .get("/api/v1/transactions")
        .then()
        .statusCode(200)
        .body("total", equalTo(currentCount + 3))
        .body("offset", equalTo(0))
        .body("limit", equalTo(20))
        .body("hasNext", equalTo(false));
  }

  @Test
  @Order(6)
  void shouldSupportPagination() {
    // Get current transaction count
    int currentCount =
        given().when().get("/api/v1/transactions").then().statusCode(200).extract().path("total");

    // Create multiple transactions
    for (int i = 0; i < 5; i++) {
      given()
          .contentType(MediaType.APPLICATION_JSON)
          .body(
              String.format(
                  """
                        {
                            "amount": %d.00,
                            "currency": "USD",
                            "originAccountNumber": "ACC-%09d",
                            "destinationAccountNumber": "ACC-%09d"
                        }
                        """,
                  (i + 1) * 10, 300000000 + i, 400000000 + i))
          .when()
          .post("/api/v1/transactions")
          .then()
          .statusCode(201);
    }

    // Test pagination - expect current count + 5 new transactions
    given()
        .queryParam("offset", 0)
        .queryParam("limit", 2)
        .when()
        .get("/api/v1/transactions")
        .then()
        .statusCode(200)
        .body("transactions", hasSize(2))
        .body("total", equalTo(currentCount + 5))
        .body("offset", equalTo(0))
        .body("limit", equalTo(2))
        .body("hasNext", equalTo(true));
  }
}
