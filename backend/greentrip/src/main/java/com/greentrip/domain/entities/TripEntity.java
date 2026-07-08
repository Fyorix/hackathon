package com.greentrip.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TripEntity(
    Long id,
    Double distanceKm,
    Double co2Saved,
    Integer pointsEarned,
    TransportType type,
    String status,
    Long userId,
    LocalDateTime createdAt,
    Long stravaActivityId,
    LocalDate tripDate
) {}
