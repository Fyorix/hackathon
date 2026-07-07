package com.greentrip.api;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.requests.UpdateCompanyRequest;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.infra.repositories.CompanyRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Integration tests for /api/companies. Runs against the real Postgres test database
 * (Quarkus dev services / testcontainers) to verify data is actually persisted.
 */
@QuarkusTest
public class CompanyResourceIT {

    @Inject
    CompanyRepository companyRepository;

    @Inject
    EntityManager entityManager;

    private CompanyModel existingCompany;

    @BeforeEach
    @Transactional
    public void setUp() {
        companyRepository.delete("name != ?1", "Takima");

        existingCompany = new CompanyModel();
        existingCompany.name = "EPITA";
        existingCompany.sirenNumber = "222222222";
        existingCompany.totalEmployees = 1000;
        companyRepository.persist(existingCompany);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testCreateCompanyIntegrationFlow() {
        // Arrange
        CreateCompanyRequest request = new CreateCompanyRequest("VeloFlex", "111222333", "veloflex.png", 48.8566, 2.3522);

        // Act
        Long createdId = given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/companies")
        .then()
          .statusCode(201)
          .body("name", is("VeloFlex"))
          .body("sirenNumber", is("111222333"))
          .body("id", notNullValue())
          .extract().jsonPath().getLong("id");

        // Assert: verify persistence in DB
        entityManager.clear();
        CompanyModel persisted = companyRepository.findById(createdId);
        Assertions.assertNotNull(persisted);
        Assertions.assertEquals("VeloFlex", persisted.name);
        Assertions.assertEquals("111222333", persisted.sirenNumber);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testCreateCompanyDuplicateNameReturnsConflict() {
        // Arrange
        CreateCompanyRequest request = new CreateCompanyRequest("EPITA", "999999999", null, null, null);

        // Act & Assert
        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/companies")
        .then()
          .statusCode(409);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testCreateCompanyInvalidPayloadReturnsBadRequest() {
        // Arrange
        CreateCompanyRequest request = new CreateCompanyRequest("", "", null, null, null);

        // Act & Assert
        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/companies")
        .then()
          .statusCode(400);
    }

    @Test
    public void testGetCompanyByIdIntegrationFlow() {
        // Act & Assert
        given()
        .when()
          .get("/api/companies/" + existingCompany.id)
        .then()
          .statusCode(200)
          .body("id", is(existingCompany.id.intValue()))
          .body("name", is("EPITA"));
    }

    @Test
    public void testGetCompanyByIdNotFoundReturns404() {
        given()
        .when()
          .get("/api/companies/999999")
        .then()
          .statusCode(404);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testUpdateCompanyIntegrationFlow() {
        // Arrange
        UpdateCompanyRequest request = new UpdateCompanyRequest("EPITA Renamed", "new-logo.png", 48.9, 2.4);

        // Act
        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .put("/api/companies/" + existingCompany.id)
        .then()
          .statusCode(200)
          .body("name", is("EPITA Renamed"));

        // Assert: verify persistence in DB
        entityManager.clear();
        CompanyModel updated = companyRepository.findById(existingCompany.id);
        Assertions.assertEquals("EPITA Renamed", updated.name);
        Assertions.assertEquals("new-logo.png", updated.logoPath);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testUpdateCompanyNotFoundReturns404() {
        UpdateCompanyRequest request = new UpdateCompanyRequest("Ghost", null, null, null);

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .put("/api/companies/999999")
        .then()
          .statusCode(404);
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testDeleteCompanyIntegrationFlow() {
        // Act
        given()
        .when()
          .delete("/api/companies/" + existingCompany.id)
        .then()
          .statusCode(204);

        // Assert: verify removal from DB
        entityManager.clear();
        Assertions.assertNull(companyRepository.findById(existingCompany.id));
    }

    @Test
    @TestSecurity(user = "admin@takima.fr", roles = {"ADMIN"})
    public void testDeleteCompanyNotFoundReturns404() {
        given()
        .when()
          .delete("/api/companies/999999")
        .then()
          .statusCode(404);
    }

    @Test
    public void testGetCompanyUserLeaderboard() {
        // We can query company ID 1 (Takima) which is loaded by import.sql and check pagination/sorting
        given()
          .queryParam("page", 0)
          .queryParam("size", 5)
          .queryParam("sortBy", "CO2")
          .queryParam("desc", true)
        .when()
          .get("/api/companies/1/leaderboard")
        .then()
          .statusCode(200)
          .body("size()", is(5))
          .body("[0].name", notNullValue())
          .body("[0].totalCo2Saved", notNullValue());

        // Test sorting by points
        given()
          .queryParam("page", 0)
          .queryParam("size", 3)
          .queryParam("sortBy", "POINTS")
          .queryParam("desc", true)
        .when()
          .get("/api/companies/1/leaderboard")
        .then()
          .statusCode(200)
          .body("size()", is(3));

        // Test company not found
        given()
        .when()
          .get("/api/companies/999999/leaderboard")
        .then()
          .statusCode(404);
    }
}
