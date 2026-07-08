package com.greentrip.infra.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;

/**
 * Maps a single activity from Strava's GET /athlete/activities response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record StravaActivity(
    Long id,
    String name,
    String type,
    @JsonProperty("sport_type") String sportType,
    Double distance,
    @JsonProperty("start_date") Instant startDate,
    @JsonProperty("start_date_local") Instant startDateLocal,
    @JsonProperty("start_latlng") List<Double> startLatLng,
    @JsonProperty("end_latlng") List<Double> endLatLng
) {}
