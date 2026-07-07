package com.greentrip.domain.dtos.responses;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CompanyResponse", description = "Company details returned by the API")
public record CompanyResponse(
    @Schema(description = "Unique identifier", example = "3")
    Long id,
    @Schema(description = "Name of the company", example = "Takima")
    String name,
    @Schema(description = "SIREN number of the company", example = "123456789")
    String sirenNumber,
    @Schema(description = "Total number of employees", example = "50")
    Integer totalEmployees,
    @Schema(description = "Total CO2 saved by all employees in kilograms", example = "145.2")
    Double totalCo2Saved,
    @Schema(description = "Total points accumulated by the company", example = "1200")
    Integer totalPoints,
    @Schema(description = "Total kilometers traveled by all employees", example = "3500.5")
    Double totalKm,
    @Schema(description = "Path to the company's logo image", example = "/images/logos/takima.png")
    String logoPath
) {}
