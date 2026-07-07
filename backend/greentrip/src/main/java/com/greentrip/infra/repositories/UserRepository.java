package com.greentrip.infra.repositories;

import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.models.UserModel;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class UserRepository extends AbstractBaseRepository<UserModel, UserEntity> {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    /**
     * Finds a user by their email address and maps it to a UserEntity.
     */
    public Optional<UserEntity> findByEmail(String email) {
        log.debug("Database query: Fetching user by email: {}", email);
        return findEntityByField("email", email);
    }

    /**
     * Deletes a user by email.
     */
    public void deleteByEmail(String email) {
        log.debug("Database action: Deleting user by email: {}", email);
        delete("email", email);
    }
}
