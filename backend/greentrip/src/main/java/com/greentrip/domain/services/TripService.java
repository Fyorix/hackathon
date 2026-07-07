package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.TripRequest;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.mappers.TripMapper;
import com.greentrip.infra.repositories.TripRepository;
import com.greentrip.infra.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class TripService {

    private static final Logger log = LoggerFactory.getLogger(TripService.class);

    @Inject
    TripRepository tripRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    TripMapper tripMapper;

    /**
     * Declares a new trip and updates user/company stats.
     */
    @Transactional
    public TripEntity declareTrip(String userEmail, TripRequest request) {
        log.info("Declaring trip for user: {}", userEmail);
        log.debug("Trip details to calculate & persist: distance={} km, type={}", request.distanceKm(), request.type());
        // TODO: implement business logic for trip declaration
        return null;
    }

    /**
     * Gets user's trips paginated and sorted.
     */
    public List<TripEntity> getTrips(String userEmail, int page, int size, String sortBy, boolean descending) {
        log.info("Fetching trips for user: {} (page: {}, size: {}, sortBy: {}, desc: {})", userEmail, page, size, sortBy, descending);
        // TODO: implement business logic to retrieve paginated trips
        return List.of();
    }

    /**
     * Deletes a trip.
     */
    @Transactional
    public void deleteTrip(String userEmail, Long id) {
        log.info("Deleting trip ID {} for user {}", id, userEmail);
        // TODO: implement trip deletion
    }
}
