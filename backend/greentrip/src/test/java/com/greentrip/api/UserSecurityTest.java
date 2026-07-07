package com.greentrip.api;

import com.greentrip.domain.dtos.requests.LoginRequest;
import com.greentrip.domain.dtos.requests.RegisterRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UserSecurityTest {

    // --- PUBLIC ENDPOINTS (PermitAll) ---

    @Test
    public void testRegisterIsPublic() {
        RegisterRequest request = new RegisterRequest("", "", "", null); // Invalid but reaches validator (400 Bad Request, not 401)
        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/users/register")
        .then()
          .statusCode(400); 
    }

    @Test
    public void testLoginIsPublic() {
        LoginRequest request = new LoginRequest("", ""); // Invalid but reaches validator (400 Bad Request, not 401)
        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/users/login")
        .then()
          .statusCode(400);
    }

    // --- SECURED ENDPOINTS (Authenticated / Default) ---

    @Test
    public void testGetMeRequiresAuthentication() {
        given()
        .when()
          .get("/api/users/me")
        .then()
          .statusCode(401);
    }

    @Test
    public void testUpdateMeRequiresAuthentication() {
        given()
          .contentType(ContentType.JSON)
          .body("{}")
        .when()
          .put("/api/users/me")
        .then()
          .statusCode(401);
    }

    @Test
    public void testDeleteMeRequiresAuthentication() {
        given()
        .when()
          .delete("/api/users/me")
        .then()
          .statusCode(401);
    }

    @Test
    public void testJoinCompanyRequiresAuthentication() {
        given()
          .contentType(ContentType.JSON)
          .body("{\"companyId\": 1}")
        .when()
          .post("/api/users/join-company")
        .then()
          .statusCode(401);
    }

    // --- ROLE SECURED ENDPOINTS (RolesAllowed("ADMIN")) ---

    @Test
    public void testDeleteUserByIdRequiresAuthentication() {
        given()
        .when()
          .delete("/api/users/999")
        .then()
          .statusCode(401);
    }

    @Test
    @TestSecurity(user = "employee@takima.fr", roles = {"USER"})
    public void testDeleteUserByIdForbiddenForStandardUser() {
        given()
        .when()
          .delete("/api/users/999")
        .then()
          .statusCode(403); // Forbidden
    }
}
