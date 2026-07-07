package com.greentrip.api;

import com.greentrip.domain.dtos.requests.TripRequest;
import com.greentrip.domain.entities.TransportType;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.domain.models.UserModel;
import com.greentrip.infra.repositories.CompanyRepository;
import com.greentrip.infra.repositories.TripRepository;
import com.greentrip.infra.repositories.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class TripResourceTest {

    @Inject
    TripRepository tripRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CompanyRepository companyRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        tripRepository.deleteAll();
        userRepository.deleteAll();
        companyRepository.deleteAll();

        CompanyModel company = new CompanyModel();
        company.name = "Takima";
        company.sirenNumber = "123456789";
        company.totalEmployees = 1;
        companyRepository.persist(company);

        UserModel user = new UserModel();
        user.name = "Alex";
        user.email = "alex@takima.fr";
        user.password = "password123";
        user.role = "USER";
        user.company = company;
        userRepository.persist(user);
    }

    @Test
    public void testEndpointsRequireAuthentication() {
        // GET /api/trips
        given()
        .when()
          .get("/api/trips")
        .then()
          .statusCode(401);

        // POST /api/trips
        given()
          .contentType(ContentType.JSON)
          .body(new TripRequest(10.0, TransportType.VELO))
        .when()
          .post("/api/trips")
        .then()
          .statusCode(401);

        // DELETE /api/trips/1
        given()
        .when()
          .delete("/api/trips/1")
        .then()
          .statusCode(401);
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testCreateTripSuccess() {
        TripRequest request = new TripRequest(12.0, TransportType.VELO);

        given()
          .contentType(ContentType.JSON)
          .body(request)
        .when()
          .post("/api/trips")
        .then()
          .statusCode(201)
          .body("id", notNullValue())
          .body("distanceKm", is(12.0f))
          .body("co2Saved", is(2.4f))
          .body("pointsEarned", is(120))
          .body("type", is("VELO"));
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testCreateTripValidationError() {
        // Negative distance
        TripRequest requestInvalidDistance = new TripRequest(-5.0, TransportType.VELO);
        given()
          .contentType(ContentType.JSON)
          .body(requestInvalidDistance)
        .when()
          .post("/api/trips")
        .then()
          .statusCode(400);

        // Unknown transport type
        String requestInvalidType = "{\"distanceKm\": 10.0, \"type\": \"AVION\"}";
        given()
          .contentType(ContentType.JSON)
          .body(requestInvalidType)
        .when()
          .post("/api/trips")
        .then()
          .statusCode(400);
    }

    @Test
    @TestSecurity(user = "alex@takima.fr", roles = {"USER"})
    public void testGetTripsHistory() {
        // Declare a trip first
        given()
          .contentType(ContentType.JSON)
          .body(new TripRequest(10.0, TransportType.VELO))
        .when()
          .post("/api/trips")
        .then()
          .statusCode(201);

        // Fetch history
        given()
        .when()
          .get("/api/trips")
        .then()
          .statusCode(200)
          .body("size()", is(1))
          .body("[0].distanceKm", is(10.0f))
          .body("[0].pointsEarned", is(100));
    }
}
