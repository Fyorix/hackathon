package com.greentrip.domain.services;

import com.greentrip.domain.dtos.requests.TripRequest;
import com.greentrip.domain.entities.TransportType;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.mappers.TripMapper;
import com.greentrip.domain.mappers.UserMapper;
import com.greentrip.domain.models.CompanyModel;
import com.greentrip.domain.models.TripModel;
import com.greentrip.domain.models.UserModel;
import com.greentrip.infra.repositories.TripRepository;
import com.greentrip.infra.repositories.UserRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
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

    @Inject
    UserMapper userMapper;

    /**
     * Declares a new trip and updates user/company stats.
     */
    @Transactional
    public TripEntity declareTrip(String userEmail, TripRequest request) {
        log.info("Declaring trip for user: {}", userEmail);
        log.debug("Trip details to calculate & persist: distance={} km, type={}", request.distanceKm(), request.type());
        
        UserModel userModel = userRepository.findByEmail(userEmail)
                .map(userMapper::toModel)
                .orElseThrow(() -> new WebApplicationException("User not found", Response.Status.NOT_FOUND));

        TransportType transportType = request.type();
        double distanceKm = request.distanceKm();
        double co2Saved = transportType.calculateCo2Saved(distanceKm);
        int pointsEarned = transportType.calculatePointsEarned(distanceKm);

        TripEntity tripEntity = new TripEntity(
                null,
                distanceKm,
                co2Saved,
                pointsEarned,
                transportType,
                "COMPLETED",
                userModel.id,
                java.time.LocalDateTime.now(),
                null,
                java.time.LocalDate.now()
        );
        TripEntity createdTrip = tripRepository.create(tripEntity);

        userModel.carbonPointsBalance += pointsEarned;
        userModel.totalCo2Saved += co2Saved;
        userModel.totalKm += distanceKm;

        if (userModel.company != null) {
            CompanyModel company = userModel.company;
            company.totalKm += distanceKm;
            company.totalCo2Saved += co2Saved;
            company.totalPoints += pointsEarned;
        }

        return createdTrip;
    }

    /**
     * Gets user's trips paginated and sorted.
     */
    public List<TripEntity> getTrips(String userEmail, int page, int size, String sortBy, boolean descending) {
        log.info("Fetching trips for user: {} (page: {}, size: {}, sortBy: {}, desc: {})", userEmail, page, size, sortBy, descending);
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return tripRepository.find("user.email", sort, userEmail)
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(tripMapper::toEntity)
                .toList();
    }

    /**
     * Deletes a trip.
     */
    @Transactional
    public void deleteTrip(String userEmail, Long id) {
        log.info("Deleting trip ID {} for user {}", id, userEmail);
        TripModel tripModel = tripRepository.findByIdOptional(id)
                .orElseThrow(() -> new WebApplicationException("Trip not found", Response.Status.NOT_FOUND));

        if (!tripModel.user.email.equals(userEmail)) {
            throw new WebApplicationException("Trip not found", Response.Status.NOT_FOUND);
        }

        UserModel userModel = tripModel.user;
        userModel.carbonPointsBalance = Math.max(0, userModel.carbonPointsBalance - tripModel.pointsEarned);
        userModel.totalCo2Saved = Math.max(0.0, userModel.totalCo2Saved - tripModel.co2Saved);
        userModel.totalKm = Math.max(0.0, userModel.totalKm - tripModel.distanceKm);

        if (userModel.company != null) {
            CompanyModel company = userModel.company;
            company.totalKm = Math.max(0.0, company.totalKm - tripModel.distanceKm);
            company.totalCo2Saved = Math.max(0.0, company.totalCo2Saved - tripModel.co2Saved);
            company.totalPoints = Math.max(0, company.totalPoints - tripModel.pointsEarned);
        }

        tripRepository.delete(tripModel);
    }
}
