package com.greentrip.domain.entities;

import java.time.LocalDateTime;

public record CompanyEntity(
    Long id,
    String name,
    String sirenNumber,
    Integer totalEmployees,
    Double totalCo2Saved,
    Integer totalPoints,
    Double totalKm,
    LocalDateTime createdAt,
    Double latitude,
    Double longitude,
    String logoPath
) {}
