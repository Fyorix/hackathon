package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.RegisterRequest;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.domain.models.UserModel;
import com.greentrip.infra.repositories.CompanyRepository;
import com.greentrip.infra.repositories.UserRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UserServiceIT {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @Inject
    CompanyRepository companyRepository;

    @Inject
    EntityManager entityManager;

    private CompanyModel testCompany;

    @BeforeEach
    @Transactional
    public void setUp() {
        userRepository.deleteAll();
        companyRepository.deleteAll();

        testCompany = new CompanyModel();
        testCompany.name = "Takima";
        testCompany.totalEmployees = 0;
        companyRepository.persist(testCompany);
    }

    @Test
    public void testRegisterUserIntegrationFlow() {
        RegisterRequest request = new RegisterRequest(
            "Alex",
            "alex@takima.fr",
            "password123",
            testCompany.id
        );

        // Action
        UserEntity created = userService.register(request);

        // Assertions
        Assertions.assertNotNull(created);
        Assertions.assertNotNull(created.id());
        Assertions.assertEquals("Alex", created.name());
        Assertions.assertEquals("alex@takima.fr", created.email());
        Assertions.assertEquals("USER", created.role());
        Assertions.assertEquals(0, created.carbonPointsBalance());
        Assertions.assertEquals(0.0, created.totalCo2Saved());

        // Clear hibernate cache to force fresh reload from DB
        entityManager.clear();

        // Verify DB persistence
        UserModel databaseModel = userRepository.findById(created.id());
        Assertions.assertNotNull(databaseModel);
        Assertions.assertEquals("Alex", databaseModel.name);
        Assertions.assertEquals("password123", databaseModel.password);
        Assertions.assertEquals(testCompany.id, databaseModel.company.id);

        // Verify company count incremented
        CompanyModel companyInDb = companyRepository.findById(testCompany.id);
        Assertions.assertEquals(1, companyInDb.totalEmployees);
    }

    @Test
    public void testRegisterUserDuplicateEmailThrowsConflict() {
        RegisterRequest request = new RegisterRequest(
            "Alex",
            "alex@takima.fr",
            "password123",
            testCompany.id
        );

        userService.register(request);

        // Clear cache
        entityManager.clear();

        // Attempting to register twice with same email throws 409
        Assertions.assertThrows(WebApplicationException.class, () -> {
            userService.register(request);
        });
    }

    @Test
    public void testDeleteUserIntegrationFlow() {
        RegisterRequest request = new RegisterRequest(
            "Alex",
            "alex@takima.fr",
            "password123",
            testCompany.id
        );

        UserEntity created = userService.register(request);

        // Clear hibernate cache
        entityManager.clear();
        Assertions.assertEquals(1, companyRepository.findById(testCompany.id).totalEmployees);

        // Action
        userService.deleteUserByEmail(created.email());

        // Clear hibernate cache
        entityManager.clear();

        // Assertions
        Assertions.assertFalse(userRepository.findByEmail(created.email()).isPresent());
        Assertions.assertEquals(0, companyRepository.findById(testCompany.id).totalEmployees);
    }
}
