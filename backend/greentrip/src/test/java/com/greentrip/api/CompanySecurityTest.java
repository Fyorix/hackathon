package com.greentrip.api;

import com.greentrip.domain.dtos.requests.CreateCompanyRequest;
import com.greentrip.domain.dtos.requests.UpdateCompanyRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Verifies role-based access control on /api/companies admin-only endpoints.
 */
@QuarkusTest
public class CompanySecurityTest {

    @Test
    public void testCreateCompanyRequiresAuthentication() {
        CreateCompanyRequest request = new CreateCompanyRequest("Any", "123456789", null);

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/companies")
        .then()
          .statusCode(401);
    }

    @Test
    @TestSecurity(user = "employee@takima.fr", roles = {"USER"})
    public void testCreateCompanyForbiddenForStandardUser() {
        CreateCompanyRequest request = new CreateCompanyRequest("Any", "123456789", null);

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/companies")
        .then()
          .statusCode(403);
    }

    @Test
    public void testUpdateCompanyRequiresAuthentication() {
        UpdateCompanyRequest request = new UpdateCompanyRequest("Any", null);

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .put("/api/companies/1")
        .then()
          .statusCode(401);
    }

    @Test
    public void testDeleteCompanyRequiresAuthentication() {
        given()
        .when()
          .delete("/api/companies/1")
        .then()
          .statusCode(401);
    }

    @Test
    @TestSecurity(user = "employee@takima.fr", roles = {"USER"})
    public void testDeleteCompanyForbiddenForStandardUser() {
        given()
        .when()
          .delete("/api/companies/1")
        .then()
          .statusCode(403);
    }

    @Test
    public void testGetCompanyByIdIsPublic() {
        // Endpoint is @PermitAll: unauthenticated access must reach the handler (never 401/403),
        // regardless of whether id 1 currently exists (200) or not (404).
        int status = given()
        .when()
          .get("/api/companies/1")
        .then()
          .extract().statusCode();

        org.junit.jupiter.api.Assertions.assertTrue(status == 200 || status == 404,
                "Expected 200 or 404 but got " + status);
    }
}
