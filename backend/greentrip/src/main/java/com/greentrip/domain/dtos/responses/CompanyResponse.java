package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CompanyResponse", description = "Company details returned by the API")
public record CompanyResponse(
    @Schema(description = "Unique identifier", example = "3")
    Long id,
    @Schema(description = "Name of the company", example = "Takima")
    String name,
    @Schema(description = "Total number of employees", example = "50")
    Integer totalEmployees,
    @Schema(description = "Total CO2 saved by all employees in kilograms", example = "145.2")
    Double totalCo2Saved,
    @Schema(description = "RSE badge unlocked", example = "🏆 Top Green 2026")
    String unlockedBadge
) {}
