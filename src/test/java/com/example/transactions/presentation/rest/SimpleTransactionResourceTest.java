package com.example.transactions.presentation.rest;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

@QuarkusTest
class SimpleTransactionResourceTest {

  @Test
  void shouldReturnHealthCheck() {
    given().when().get("/q/health/ready").then().statusCode(200);
  }
}
