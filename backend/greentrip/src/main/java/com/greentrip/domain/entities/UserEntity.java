package com.greentrip.domain.entities;

import java.time.LocalDateTime;

public record UserEntity(
    Long id,
    String name,
    String email,
    String password,
    String role,
    Integer carbonPointsBalance,
    Double totalCo2Saved,
    Double totalKm,
    Long companyId,
    LocalDateTime createdAt,
    Long stravaAthleteId,
    String stravaAccessToken,
    String stravaRefreshToken,
    Long stravaTokenExpiresAt
) {}
