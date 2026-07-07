package com.greentrip.infra.repositories;

import com.greentrip.domain.models.UserModel;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserRepository implements BaseRepository<UserModel> {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    
    /**
     * Finds a user by their email address.
     * @param email The user's email address
     * @return An optional containing the user if found
     */
    public Optional<UserModel> findByEmail(String email) {
        log.debug("Database query: Fetching user by email: {}", email);
        return find("email", email).firstResultOptional();
    }
}
