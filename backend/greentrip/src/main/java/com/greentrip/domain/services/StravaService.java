package com.greentrip.domain.services;

import com.greentrip.domain.entities.UserEntity;
import com.greentrip.infra.client.StravaClient;
import com.greentrip.infra.client.StravaTokenResponse;
import com.greentrip.infra.repositories.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class StravaService {

    private static final Logger log = LoggerFactory.getLogger(StravaService.class);

    @Inject
    StravaClient stravaClient;

    @Inject
    UserRepository userRepository;

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
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Strava link failed: User with email {} not found", email);
                    return new WebApplicationException("User not found", 404);
                });

        StravaTokenResponse token = stravaClient.exchangeAuthorizationCode(code);

        UserEntity updated = new UserEntity(
                user.id(),
                user.name(),
                user.email(),
                user.password(),
                user.role(),
                user.carbonPointsBalance(),
                user.totalCo2Saved(),
                user.totalKm(),
                user.workLat(),
                user.workLng(),
                user.workStartTime(),
                user.workEndTime(),
                user.companyId(),
                user.createdAt(),
                token.athlete() != null ? token.athlete().id() : null,
                token.accessToken(),
                token.refreshToken(),
                token.expiresAt()
        );

        userRepository.update(updated);
        log.info("Successfully linked Strava account for user: {}", email);
    }
}
