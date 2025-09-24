package com.example.transactions.presentation.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import jakarta.ws.rs.core.MediaType;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

import org.junit.jupiter.api.Test;

/** Fast integration test profile that disables potentially problematic features. */
public class FastTestProfile implements io.quarkus.test.junit.QuarkusTestProfile {
  @Override
  public java.util.Map<String, String> getConfigOverrides() {
    return java.util.Map.of(
        "quarkus.log.level", "WARN",
        "quarkus.http.port", "0", // Use random port
        "quarkus.devservices.enabled", "false",
        "app.repository.type", "in-memory");
  }
}

@QuarkusTest
@TestProfile(FastTestProfile.class)
class FastTransactionResourceTest {

  @Test
  void shouldCreateTransactionSuccessfully() {
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
        .body("id", notNullValue())
        .body("amount", equalTo(100.5f))
        .body("currency", equalTo("USD"))
        .body("originAccountNumber", equalTo("ACC-123456789"))
        .body("destinationAccountNumber", equalTo("ACC-987654321"))
        .body("status", equalTo("PENDING"));
  }

  @Test
  void shouldReturn404ForNonExistentTransaction() {
    given().when().get("/api/v1/transactions/non-existent-id").then().statusCode(404);
  }
}
