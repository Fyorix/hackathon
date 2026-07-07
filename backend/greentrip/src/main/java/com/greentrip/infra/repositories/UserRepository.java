package com.greentrip.infra.repositories;

import com.greentrip.domain.entities.UserEntity;
import com.greentrip.domain.entities.UserSortField;
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

    /**
     * Retrieves the sorted and paged leaderboard of users for a given company.
     */
    public java.util.List<UserEntity> findPagedLeaderboardByCompany(Long companyId, int page, int size, UserSortField sortBy, boolean descending) {
        String sortField = sortBy.getFieldName();
        String direction = descending ? "desc" : "asc";
        log.debug("Database query: Fetching company {} users leaderboard (page={}, size={}, sort={}, desc={})", 
                companyId, page, size, sortField, descending);
        
        return find("company.id = ?1 order by " + sortField + " " + direction, companyId)
                .page(page, size)
                .list()
                .stream()
                .map(mapper::toEntity)
                .toList();
    }

    /**
     * Finds users in a specific company whose name matches the query, case-insensitive, paginated.
     */
    public java.util.List<UserEntity> findPagedUsersByNameQueryAndCompany(Long companyId, String query, int page, int size) {
        log.debug("Database query: Searching users matching '{}' in company {} (page={}, size={})", query, companyId, page, size);
        return find("company.id = ?1 and lower(name) like lower(?2)", companyId, "%" + query + "%")
                .page(page, size)
                .list()
                .stream()
                .map(mapper::toEntity)
                .toList();
    }
}
