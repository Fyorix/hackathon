package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.RegisterRequest;
import com.greentrip.domain.dtos.requests.TripRequest;
import com.greentrip.domain.entities.TransportType;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.domain.models.TripModel;
import com.greentrip.domain.models.UserModel;
import com.greentrip.infra.repositories.CompanyRepository;
import com.greentrip.infra.repositories.TripRepository;
import com.greentrip.infra.repositories.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

@QuarkusTest
public class TripServiceIT {

    @Inject
    TripService tripService;

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    TripRepository tripRepository;

    @Inject
    EntityManager entityManager;

    private CompanyModel testCompany;
    private UserEntity testUser;

    @BeforeEach
    @Transactional
    public void setUp() {
        tripRepository.deleteAll();
        userRepository.deleteAll();
        companyRepository.deleteAll();

        testCompany = new CompanyModel();
        testCompany.name = "Takima";
        testCompany.sirenNumber = "123456789";
        testCompany.totalEmployees = 0;
        testCompany.totalKm = 0.0;
        testCompany.totalCo2Saved = 0.0;
        testCompany.totalPoints = 0;
        companyRepository.persist(testCompany);

        RegisterRequest registerRequest = new RegisterRequest(
            "Alex",
            "alex@takima.fr",
            "password123",
            testCompany.id
        );
        testUser = userService.register(registerRequest);

        entityManager.clear();
    }

    @Test
    public void testDeclareTripFlow() {
        TripRequest request = new TripRequest(12.0, TransportType.VELO);

        // Action
        TripEntity created = tripService.declareTrip("alex@takima.fr", request);

        // Assertions on returned Entity
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.id());
        Assertions.assertEquals(12.0, created.distanceKm());
        Assertions.assertEquals(2.4, created.co2Saved(), 0.001); // 12 * 0.20
        Assertions.assertEquals(120, created.pointsEarned());     // 12 * 10
        Assertions.assertEquals("VELO", created.type());
        Assertions.assertEquals("COMPLETED", created.status());

        // Clear hibernate cache to force fresh reload from DB
        entityManager.clear();

        // Verify DB persistence for trip
        TripModel tripInDb = tripRepository.findById(created.id());
        Assertions.assertNotNull(tripInDb);
        Assertions.assertEquals(12.0, tripInDb.distanceKm);

        // Verify User statistics updated
        UserModel userInDb = userRepository.findById(testUser.id());
        Assertions.assertEquals(120, userInDb.carbonPointsBalance);
        Assertions.assertEquals(2.4, userInDb.totalCo2Saved, 0.001);

        // Verify Company statistics updated
        CompanyModel companyInDb = companyRepository.findById(testCompany.id);
        Assertions.assertEquals(12.0, companyInDb.totalKm, 0.001);
        Assertions.assertEquals(2.4, companyInDb.totalCo2Saved, 0.001);
        Assertions.assertEquals(120, companyInDb.totalPoints);
    }

    @Test
    public void testGetTripsPagingAndSorting() {
        tripService.declareTrip("alex@takima.fr", new TripRequest(5.0, TransportType.VELO)); // 50 pts, 1.0 CO2
        tripService.declareTrip("alex@takima.fr", new TripRequest(10.0, TransportType.MARCHE)); // 120 pts, 2.5 CO2

        entityManager.clear();

        // Retrieve sorted desc (default is desc = true by date, let's sort by distanceKm desc)
        List<TripEntity> trips = tripService.getTrips("alex@takima.fr", 0, 10, "distanceKm", true);
        Assertions.assertEquals(2, trips.size());
        Assertions.assertEquals(10.0, trips.get(0).distanceKm());
        Assertions.assertEquals(5.0, trips.get(1).distanceKm());

        // Retrieve sorted asc
        List<TripEntity> tripsAsc = tripService.getTrips("alex@takima.fr", 0, 10, "distanceKm", false);
        Assertions.assertEquals(2, tripsAsc.size());
        Assertions.assertEquals(5.0, tripsAsc.get(0).distanceKm());
        Assertions.assertEquals(10.0, tripsAsc.get(1).distanceKm());
    }

    @Test
    public void testDeleteTripFlow() {
        TripEntity created = tripService.declareTrip("alex@takima.fr", new TripRequest(10.0, TransportType.VELO)); // 100 pts, 2.0 CO2
        
        entityManager.clear();
        
        // Delete trip
        tripService.deleteTrip("alex@takima.fr", created.id());

        entityManager.clear();

        // Verify trip is deleted
        Assertions.assertNull(tripRepository.findById(created.id()));

        // Verify User statistics reverted
        UserModel userInDb = userRepository.findById(testUser.id());
        Assertions.assertEquals(0, userInDb.carbonPointsBalance);
        Assertions.assertEquals(0.0, userInDb.totalCo2Saved, 0.001);

        // Verify Company statistics reverted
        CompanyModel companyInDb = companyRepository.findById(testCompany.id);
        Assertions.assertEquals(0.0, companyInDb.totalKm, 0.001);
        Assertions.assertEquals(0.0, companyInDb.totalCo2Saved, 0.001);
        Assertions.assertEquals(0, companyInDb.totalPoints);
    }

    @Test
    public void testDeleteTripForbiddenForOtherUser() {
        TripEntity created = tripService.declareTrip("alex@takima.fr", new TripRequest(10.0, TransportType.VELO));
        
        // Attempt to delete with other user email should throw Forbidden
        Assertions.assertThrows(WebApplicationException.class, () -> {
            tripService.deleteTrip("other@takima.fr", created.id());
        });
    }
}
