package com.greentrip.api;

import com.greentrip.domain.dtos.requests.LoginRequest;
import com.greentrip.domain.dtos.requests.RegisterRequest;
import com.greentrip.domain.dtos.requests.UserRequest;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusIntegrationTest
public class UserResourceIT {

    @Test
    public void testRegisterIntegrationFlow() {
        RegisterRequest request = new RegisterRequest(
            "New User", 
            "newuser@takima.fr", 
            "password123", 
            1L // Uses company ID 1 seeded via import.sql
        );

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/users/register")
        .then()
          .statusCode(201)
          .body("name", is("New User"))
          .body("email", is("newuser@takima.fr"));
    }

    @Test
    public void testLoginIntegrationFlow() {
        LoginRequest request = new LoginRequest("alex@takima.fr", "password123");

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/users/login")
        .then()
          .statusCode(200)
          .body("token", notNullValue())
          .body("tokenType", is("Bearer"));
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testGetMeIntegrationFlow() {
        given()
        .when()
          .get("/api/users/me")
        .then()
          .statusCode(200)
          .body("email", is("alex@takima.fr"));
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testUpdateMeIntegrationFlow() {
        UserRequest request = new UserRequest("Alex New", "alex.new@takima.fr");

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .put("/api/users/me")
        .then()
          .statusCode(200);
    }

    @Test
    public void testRegisterWithoutCompanyIntegrationFlow() {
        RegisterRequest request = new RegisterRequest(
            "No Company User", 
            "nocompany@takima.fr", 
            "password123", 
            null
        );

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/users/register")
        .then()
          .statusCode(201)
          .body("name", is("No Company User"))
          .body("email", is("nocompany@takima.fr"));
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testJoinCompanyIntegrationFlow() {
        java.time.LocalTime startTime = java.time.LocalTime.of(9, 0);
        java.time.LocalTime endTime = java.time.LocalTime.of(18, 0);
        com.greentrip.domain.dtos.requests.JoinCompanyRequest request = new com.greentrip.domain.dtos.requests.JoinCompanyRequest(
            1L,
            48.8566,
            2.3522,
            48.8966,
            2.3922,
            startTime,
            endTime
        );

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/users/join-company")
        .then()
          .statusCode(200);
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testDeleteMeIntegrationFlow() {
        given()
        .when()
          .delete("/api/users/me")
        .then()
          .statusCode(204);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testDeleteUserByIdIntegrationFlow() {
        given()
        .when()
          .delete("/api/users/12")
        .then()
          .statusCode(204);
    }
}
