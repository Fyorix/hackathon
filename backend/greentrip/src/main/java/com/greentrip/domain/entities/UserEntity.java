package com.greentrip.domain.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record UserEntity(
    Long id,
    String name,
    String email,
    String password,
    String role,
    Integer carbonPointsBalance,
    Double totalCo2Saved,
    Double totalKm,
    String stravaRefreshToken,
    Double homeLat,
    Double homeLng,
    Double workLat,
    Double workLng,
    LocalTime workStartTime,
    LocalTime workEndTime,
    Long companyId,
    LocalDateTime createdAt
) {}
