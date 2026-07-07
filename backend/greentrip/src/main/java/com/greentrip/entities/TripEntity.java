package com.greentrip.entities;

import java.time.LocalDateTime;

public record TripEntity(
    Long id,
    Double distanceKm,
    Double co2Saved,
    Integer pointsEarned,
    String type,
    String status,
    Long userId,
    LocalDateTime createdAt
) {}
