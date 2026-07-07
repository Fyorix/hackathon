package com.greentrip.infra.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps the JSON payload returned by Strava's POST /oauth/token endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StravaTokenResponse(
    @JsonProperty("token_type") String tokenType,
    @JsonProperty("expires_at") Long expiresAt,
    @JsonProperty("expires_in") Long expiresIn,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("access_token") String accessToken,
    StravaAthlete athlete
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record StravaAthlete(Long id) {}
}
