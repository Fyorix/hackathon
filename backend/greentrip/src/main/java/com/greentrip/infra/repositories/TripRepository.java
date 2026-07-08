package com.greentrip.infra.repositories;

import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.models.TripModel;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TripRepository extends AbstractBaseRepository<TripModel, TripEntity> {

    private static final Logger log = LoggerFactory.getLogger(TripRepository.class);

    /**
     * Counts trips already registered for a user on a given day (used for the daily import cap).
     */
    public long countByUserAndDate(Long userId, LocalDate date) {
        return count("user.id = ?1 and tripDate = ?2", userId, date);
    }

    /**
     * Checks whether a Strava activity has already been imported for a user.
     */
    public boolean existsByStravaActivityId(Long userId, Long stravaActivityId) {
        return count("user.id = ?1 and stravaActivityId = ?2", userId, stravaActivityId) > 0;
    }
}
