package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.LoginRequest;
import com.greentrip.domain.dtos.requests.RegisterRequest;
import com.greentrip.domain.dtos.requests.UserRequest;
import com.greentrip.domain.dtos.responses.TokenResponse;
import com.greentrip.domain.entities.CompanyEntity;
import com.greentrip.domain.entities.UserEntity;
import com.greentrip.infra.repositories.CompanyRepository;
import com.greentrip.infra.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    CompanyRepository companyRepository;

    /**
     * Registers a new user.
     */
    @Transactional
    public UserEntity register(RegisterRequest request) {
        log.info("Registering user: {}", request.email());

        if (userRepository.findByEmail(request.email()).isPresent()) {
            log.warn("Email already exists: {}", request.email());
            throw new WebApplicationException("Email already exists", 409);
        }

        CompanyEntity company = null;
        if (request.companyId() != null) {
            company = companyRepository.findEntityById(request.companyId())
                    .orElseThrow(() -> {
                        log.warn("Company with ID {} not found", request.companyId());
                        return new WebApplicationException("Company not found", 400);
                    });
        }

        // Create UserEntity directly in the service
        UserEntity userEntity = new UserEntity(
                null,
                request.name(),
                request.email(),
                request.password(),
                "USER",
                0,
                0.0,
                0.0,
                null,
                null,
                null,
                null,
                null,
                company != null ? company.id() : null,
                LocalDateTime.now()
        );

        if (company != null) {
            // Update company employee count on the CompanyEntity directly
            CompanyEntity updatedCompany = new CompanyEntity(
                    company.id(),
                    company.name(),
                    company.sirenNumber(),
                    Integer.valueOf((company.totalEmployees() == null ? 0 : company.totalEmployees()) + 1),
                    company.totalCo2Saved(),
                    company.totalPoints(),
                    company.totalKm(),
                    company.createdAt(),
                    company.latitude(),
                    company.longitude(),
                    company.logoPath()
            );
            companyRepository.update(updatedCompany);
        }

        // Save UserEntity via the repository create method (which calls persist internally)
        UserEntity savedUser = userRepository.create(userEntity);
        log.info("Successfully registered user: {}", request.email());
        return savedUser;
    }

    /**
     * Authenticates a user and returns a token response.
     */
    public TokenResponse login(LoginRequest request) {
        log.info("Logging in user: {}", request.email());
        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> {
                    log.warn("Authentication failed: User with email {} not found", request.email());
                    return new WebApplicationException("Invalid credentials", 401);
                });

        // Business logic check for password matching at the service layer
        if (!user.password().equals(request.password())) {
            log.warn("Authentication failed: Incorrect password for user {}", request.email());
            throw new WebApplicationException("Invalid credentials", 401);
        }

        log.info("Successfully authenticated user: {}", user.email());
        return new TokenResponse("mock-jwt-token-for-" + user.email(), "Bearer", 86400L);
    }

    /**
     * Gets profile details of the connected user.
     */
    public UserEntity getProfile(String email) {
        log.info("Fetching profile for email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Profile fetch failed: User with email {} not found", email);
                    return new WebApplicationException("User profile not found", 404);
                });
    }

    /**
     * Updates connected user's profile.
     */
    @Transactional
    public void updateProfile(String email, UserRequest request) {
        log.info("Updating user profile for: {}", email);
        UserEntity existing = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Update profile failed: User with email {} not found", email);
                    return new WebApplicationException("User profile not found", 404);
                });

        UserEntity updated = new UserEntity(
                existing.id(),
                request.name(),
                request.email(),
                existing.password(),
                existing.role(),
                existing.carbonPointsBalance(),
                existing.totalCo2Saved(),
                existing.totalKm(),
                existing.stravaRefreshToken(),
                existing.workLat(),
                existing.workLng(),
                existing.workStartTime(),
                existing.workEndTime(),
                existing.companyId(),
                existing.createdAt()
        );

        userRepository.update(updated);
        log.info("Successfully updated user profile for: {}", email);
    }

    /**
     * Deletes user account by ID.
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user ID: {}", id);
        UserEntity user = userRepository.findEntityById(id)
                .orElseThrow(() -> {
                    log.warn("Deletion failed: User with ID {} not found", id);
                    return new WebApplicationException("User not found", 404);
                });

        if (user.companyId() != null) {
            companyRepository.findEntityById(user.companyId()).ifPresent(company -> {
                CompanyEntity updated = new CompanyEntity(
                        company.id(),
                        company.name(),
                        company.sirenNumber(),
                        Integer.valueOf(Math.max(0, (company.totalEmployees() == null ? 1 : company.totalEmployees()) - 1)),
                        company.totalCo2Saved(),
                        company.totalPoints(),
                        company.totalKm(),
                        company.createdAt(),
                        company.latitude(),
                        company.longitude(),
                        company.logoPath()
                );
                companyRepository.update(updated);
            });
        }

        userRepository.deleteEntityById(id);
        log.info("Successfully deleted user ID: {}", id);
    }

    /**
     * Deletes user account by email.
     */
    @Transactional
    public void deleteUserByEmail(String email) {
        log.info("Deleting user by email: {}", email);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Deletion failed: User with email {} not found", email);
                    return new WebApplicationException("User not found", 404);
                });

        if (user.companyId() != null) {
            companyRepository.findEntityById(user.companyId()).ifPresent(company -> {
                CompanyEntity updated = new CompanyEntity(
                        company.id(),
                        company.name(),
                        company.sirenNumber(),
                        Integer.valueOf(Math.max(0, (company.totalEmployees() == null ? 1 : company.totalEmployees()) - 1)),
                        company.totalCo2Saved(),
                        company.totalPoints(),
                        company.totalKm(),
                        company.createdAt(),
                        company.latitude(),
                        company.longitude(),
                        company.logoPath()
                );
                companyRepository.update(updated);
            });
        }

        userRepository.deleteByEmail(email);
        log.info("Successfully deleted user by email: {}", email);
    }

    /**
     * Associates a user with a company and sets their commute coordinates and working hours.
     */
    @Transactional
    public UserEntity joinCompany(String email, com.greentrip.domain.dtos.requests.JoinCompanyRequest request) {
        log.info("User {} joining company ID {}", email, request.companyId());
        
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new WebApplicationException("User not found", 404));
        
        CompanyEntity company = companyRepository.findEntityById(request.companyId())
                .orElseThrow(() -> new WebApplicationException("Company not found", 400));
        
        // If user is already in a company, we should decrement that company's employee count first
        if (user.companyId() != null && !user.companyId().equals(request.companyId())) {
            companyRepository.findEntityById(user.companyId()).ifPresent(oldCompany -> {
                CompanyEntity updatedOld = new CompanyEntity(
                        oldCompany.id(),
                        oldCompany.name(),
                        oldCompany.sirenNumber(),
                        Integer.valueOf(Math.max(0, (oldCompany.totalEmployees() == null ? 1 : oldCompany.totalEmployees()) - 1)),
                        oldCompany.totalCo2Saved(),
                        oldCompany.totalPoints(),
                        oldCompany.totalKm(),
                        oldCompany.createdAt(),
                        oldCompany.latitude(),
                        oldCompany.longitude(),
                        oldCompany.logoPath()
                );
                companyRepository.update(updatedOld);
            });
        }
        
        // Increment new company's employee count if user wasn't already in it
        if (user.companyId() == null || !user.companyId().equals(request.companyId())) {
            CompanyEntity updatedNew = new CompanyEntity(
                    company.id(),
                    company.name(),
                    company.sirenNumber(),
                    Integer.valueOf((company.totalEmployees() == null ? 0 : company.totalEmployees()) + 1),
                    company.totalCo2Saved(),
                    company.totalPoints(),
                    company.totalKm(),
                    company.createdAt(),
                    company.latitude(),
                    company.longitude(),
                    company.logoPath()
            );
            companyRepository.update(updatedNew);
        }
        
        UserEntity updatedUser = new UserEntity(
                user.id(),
                user.name(),
                user.email(),
                user.password(),
                user.role(),
                user.carbonPointsBalance(),
                user.totalCo2Saved(),
                user.totalKm(),
                user.stravaRefreshToken(),
                request.workLat(),
                request.workLng(),
                request.workStartTime(),
                request.workEndTime(),
                company.id(),
                user.createdAt()
        );
        
        return userRepository.update(updatedUser);
    }
}
