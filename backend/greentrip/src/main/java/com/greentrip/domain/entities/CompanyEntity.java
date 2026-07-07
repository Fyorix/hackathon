package com.greentrip.domain.entities;

import java.time.LocalDateTime;

public record CompanyEntity(
    Long id,
    String name,
    Integer totalEmployees,
    Double totalCo2Saved,
    String unlockedBadge,
    LocalDateTime createdAt
) {}
