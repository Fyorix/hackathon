package com.greentrip.domain.services;

import com.greentrip.domain.dtos.responses.StravaCommuteCandidateResponse;
import com.greentrip.domain.dtos.responses.StravaImportResponse;
import com.greentrip.domain.entities.TransportType;
import com.greentrip.domain.entities.TripEntity;
import com.greentrip.domain.mappers.TripMapper;
import com.greentrip.domain.mappers.UserMapper;
import com.greentrip.domain.models.UserModel;
import com.greentrip.infra.client.StravaActivity;
import com.greentrip.infra.client.StravaClient;
import com.greentrip.infra.client.StravaTokenResponse;
import com.greentrip.infra.repositories.TripRepository;
import com.greentrip.infra.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class StravaService {

    private static final Logger log = LoggerFactory.getLogger(StravaService.class);

    private static final double COMMUTE_RADIUS_METERS = 500.0;
    private static final int DAILY_IMPORT_LIMIT = 2;
    private static final int LOOKBACK_DAYS = 30;
    private static final int ACTIVITIES_PER_PAGE = 50;
    private static final int MAX_PAGES = 5;
    private static final long TOKEN_REFRESH_BUFFER_SECONDS = 60;

    @Inject
    StravaClient stravaClient;

    @Inject
    UserRepository userRepository;

    @Inject
    TripRepository tripRepository;

    @Inject
    UserMapper userMapper;

    @Inject
    TripMapper tripMapper;

    /**
     * Builds the Strava OAuth2 consent URL to redirect the user to.
     */
    public String getAuthorizationUrl(String state) {
        return stravaClient.buildAuthorizationUrl(state);
    }

    /**
     * Exchanges the authorization code for a refresh/access token pair
     * and links the resulting Strava athlete to the authenticated user.
     */
    @Transactional
    public void linkAccount(String email, String code) {
        log.info("Linking Strava account for user: {}", email);
        UserModel user = loadUser(email);

        StravaTokenResponse token = stravaClient.exchangeAuthorizationCode(code);

        user.stravaAthleteId = token.athlete() != null ? token.athlete().id() : null;
        user.stravaAccessToken = token.accessToken();
        user.stravaRefreshToken = token.refreshToken();
        user.stravaTokenExpiresAt = token.expiresAt();

        log.info("Successfully linked Strava account for user: {}", email);
    }

    /**
     * Lists the user's recent Strava activities that start or end within 500m
     * of their workplace and haven't already been imported as a trip.
     */
    @Transactional
    public List<StravaCommuteCandidateResponse> listCommuteCandidates(String email) {
        UserModel user = loadUser(email);
        validateWorkLocation(user);
        ensureValidAccessToken(user);
        return fetchCandidates(user);
    }

    /**
     * Imports the selected Strava activities as trips, enforcing a 2-trips-per-day cap
     * and skipping activities that are no longer eligible or already imported.
     */
    @Transactional
    public StravaImportResponse importActivities(String email, List<Long> requestedActivityIds) {
        UserModel user = loadUser(email);
        validateWorkLocation(user);
        ensureValidAccessToken(user);

        Map<Long, StravaCommuteCandidateResponse> candidatesById = fetchCandidates(user).stream()
                .collect(Collectors.toMap(StravaCommuteCandidateResponse::stravaActivityId, c -> c));

        List<com.greentrip.domain.dtos.responses.TripResponse> imported = new ArrayList<>();
        List<StravaImportResponse.Skipped> skipped = new ArrayList<>();
        Map<LocalDate, Long> dayCounts = new HashMap<>();

        for (Long activityId : requestedActivityIds) {
            StravaCommuteCandidateResponse candidate = candidatesById.get(activityId);
            if (candidate == null) {
                skipped.add(new StravaImportResponse.Skipped(activityId, "Not an eligible commute activity"));
                continue;
            }

            long alreadyImported = dayCounts.computeIfAbsent(candidate.date(),
                    date -> tripRepository.countByUserAndDate(user.id, date));
            if (alreadyImported >= DAILY_IMPORT_LIMIT) {
                skipped.add(new StravaImportResponse.Skipped(activityId, "Daily import limit reached for " + candidate.date()));
                continue;
            }

            TripEntity created = createTripFromCandidate(user, candidate);
            dayCounts.put(candidate.date(), alreadyImported + 1);
            imported.add(tripMapper.toResponse(created));
        }

        return new StravaImportResponse(imported, skipped);
    }

    private UserModel loadUser(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toModel)
                .orElseThrow(() -> new WebApplicationException("User not found", Response.Status.NOT_FOUND));
    }

    private void validateWorkLocation(UserModel user) {
        if (user.workLat == null || user.workLng == null) {
            throw new WebApplicationException("Work location not set for user", Response.Status.BAD_REQUEST);
        }
    }

    private void ensureValidAccessToken(UserModel user) {
        if (user.stravaRefreshToken == null || user.stravaAccessToken == null) {
            throw new WebApplicationException("Strava account not linked", Response.Status.CONFLICT);
        }
        long now = Instant.now().getEpochSecond();
        if (user.stravaTokenExpiresAt == null || user.stravaTokenExpiresAt <= now + TOKEN_REFRESH_BUFFER_SECONDS) {
            log.info("Refreshing Strava access token for user: {}", user.email);
            StravaTokenResponse refreshed = stravaClient.refreshAccessToken(user.stravaRefreshToken);
            user.stravaAccessToken = refreshed.accessToken();
            user.stravaRefreshToken = refreshed.refreshToken();
            user.stravaTokenExpiresAt = refreshed.expiresAt();
        }
    }

    private List<StravaCommuteCandidateResponse> fetchCandidates(UserModel user) {
        long after = Instant.now().minus(LOOKBACK_DAYS, ChronoUnit.DAYS).getEpochSecond();
        List<StravaActivity> activities = new ArrayList<>();
        int page = 1;
        List<StravaActivity> batch;
        do {
            batch = stravaClient.listActivities(user.stravaAccessToken, after, page, ACTIVITIES_PER_PAGE);
            activities.addAll(batch);
            page++;
        } while (!batch.isEmpty() && page <= MAX_PAGES);

        List<StravaCommuteCandidateResponse> candidates = new ArrayList<>();
        for (StravaActivity activity : activities) {
            if (activity.id() == null || activity.distance() == null || activity.distance() <= 0) {
                continue;
            }

            TransportType type = mapTransportType(activity);
            if (type == null) {
                continue;
            }

            boolean startsNearWork = isNearWork(activity.startLatLng(), user);
            boolean endsNearWork = isNearWork(activity.endLatLng(), user);
            if (!startsNearWork && !endsNearWork) {
                continue;
            }

            if (tripRepository.existsByStravaActivityId(user.id, activity.id())) {
                continue;
            }

            Instant reference = activity.startDateLocal() != null ? activity.startDateLocal() : activity.startDate();
            LocalDate date = reference != null ? reference.atZone(ZoneOffset.UTC).toLocalDate() : LocalDate.now();

            candidates.add(new StravaCommuteCandidateResponse(
                    activity.id(),
                    activity.name(),
                    type,
                    round2(activity.distance() / 1000.0),
                    date,
                    startsNearWork,
                    endsNearWork
            ));
        }
        return candidates;
    }

    private boolean isNearWork(List<Double> latLng, UserModel user) {
        if (latLng == null || latLng.size() < 2) {
            return false;
        }
        double distance = GeoUtils.distanceMeters(latLng.get(0), latLng.get(1), user.workLat, user.workLng);
        return distance <= COMMUTE_RADIUS_METERS;
    }

    private TransportType mapTransportType(StravaActivity activity) {
        String sport = activity.sportType() != null ? activity.sportType() : activity.type();
        if (sport == null) {
            return null;
        }
        return switch (sport) {
            case "Ride", "EBikeRide", "VirtualRide", "Handcycle" -> TransportType.VELO;
            case "Walk", "Hike", "Run", "TrailRun" -> TransportType.MARCHE;
            default -> null;
        };
    }

    private TripEntity createTripFromCandidate(UserModel user, StravaCommuteCandidateResponse candidate) {
        double co2Saved = candidate.type().calculateCo2Saved(candidate.distanceKm());
        int pointsEarned = candidate.type().calculatePointsEarned(candidate.distanceKm());

        TripEntity tripEntity = new TripEntity(
                null,
                candidate.distanceKm(),
                co2Saved,
                pointsEarned,
                candidate.type(),
                "COMPLETED",
                user.id,
                java.time.LocalDateTime.now(),
                candidate.stravaActivityId(),
                candidate.date()
        );
        TripEntity createdTrip = tripRepository.create(tripEntity);

        user.carbonPointsBalance += pointsEarned;
        user.totalCo2Saved += co2Saved;
        user.totalKm += candidate.distanceKm();

        if (user.company != null) {
            user.company.totalKm += candidate.distanceKm();
            user.company.totalCo2Saved += co2Saved;
            user.company.totalPoints += pointsEarned;
        }

        return createdTrip;
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
