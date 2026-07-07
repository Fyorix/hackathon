package com.greentrip.entities;

import java.time.LocalDateTime;

public record UserEntity(
    Long id,
    String name,
    String email,
    String role,
    Integer carbonPointsBalance,
    Double totalCo2Saved,
    Long companyId,
    LocalDateTime createdAt
) {}
