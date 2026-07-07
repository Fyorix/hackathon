package com.greentrip.services;

import com.greentrip.dtos.requests.LoginRequest;
import com.greentrip.dtos.requests.RegisterRequest;
import com.greentrip.dtos.requests.UserRequest;
import com.greentrip.dtos.responses.TokenResponse;
import com.greentrip.entities.UserEntity;
import com.greentrip.mappers.UserMapper;
import com.greentrip.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserRepository userRepository;

    @Inject
    UserMapper userMapper;

    /**
     * Registers a new user.
     */
    @Transactional
    public UserEntity register(RegisterRequest request) {
        log.info("Registering user: {}", request.email());
        log.debug("User details to persist: Name={}, CompanyId={}", request.name(), request.companyId());
        // TODO: implement user registration (e.g. hash password, verify company, persist UserModel)
        return null;
    }

    /**
     * Authenticates a user and returns a token response.
     */
    public TokenResponse login(LoginRequest request) {
        log.info("Logging in user: {}", request.email());
        // TODO: implement user authentication and JWT generation
        return new TokenResponse("mock-jwt-token", "Bearer", 3600L);
    }

    /**
     * Gets profile details of the connected user.
     */
    public UserEntity getProfile(String email) {
        log.info("Fetching profile for email: {}", email);
        // TODO: implement business logic to fetch user profile
        return null;
    }

    /**
     * Updates connected user's profile.
     */
    @Transactional
    public void updateProfile(String email, UserRequest request) {
        log.info("Updating user profile for: {}", email);
        // TODO: implement user profile update
    }

    /**
     * Deletes user account.
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user ID: {}", id);
        // TODO: implement user deletion
    }
}
